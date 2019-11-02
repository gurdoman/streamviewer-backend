package com.adrianrossino.service;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adrianrossino.model.ChatMessage;
import com.adrianrossino.model.LiveChat;
import com.adrianrossino.model.LiveChatStatus;
import com.adrianrossino.model.YouTubeVideo;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.LiveBroadcast;
import com.google.api.services.youtube.model.LiveBroadcastListResponse;
import com.google.api.services.youtube.model.LiveChatMessage;
import com.google.api.services.youtube.model.LiveChatMessageListResponse;

@Service
public class YouTubeApiService {
    private static final Collection<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/youtube.readonly");
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";
    private static final String APPLICATION_NAME = "StreamViewer";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    	
    public static Credential authorize(final NetHttpTransport httpTransport, String clientSecret) throws Exception {
    	GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new StringReader(clientSecret));
    	FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(CREDENTIALS_DIRECTORY));
    	DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore("youtube");
    	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
    			httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setAccessType("offline").setApprovalPrompt("force").setCredentialDataStore(datastore)
                .build();
    	
  	  	return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
    
    public static YouTube getService(String clientSecret) throws Exception {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport, clientSecret);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }
    
    public static List<YouTubeVideo> getStatus(YouTube youtubeService) throws Exception{
    	System.err.println("Called service Youtube");
    	try{
    		YouTube.LiveBroadcasts.List request = youtubeService.liveBroadcasts().list("contentDetails,snippet,status");
			LiveBroadcastListResponse response = request.setBroadcastType("all")
					.setMaxResults(9L)
					.setMine(true)
					.execute();
			List<YouTubeVideo> videos = new ArrayList<YouTubeVideo>();
			YouTubeVideo video = null;
			for(LiveBroadcast b : response.getItems()) {
				video = new YouTubeVideo(
						b.getId(), 
						b.getSnippet().getChannelId(),
						b.getSnippet().getTitle(),
						b.getSnippet().getDescription(),
						Long.toString(b.getSnippet().getPublishedAt().getValue()),
						b.getSnippet().getThumbnails().getHigh().getUrl(),
						b.getSnippet().getLiveChatId(),
						b.getStatus().getLifeCycleStatus(),
						b.getContentDetails().getMonitorStream().getEmbedHtml()
						);
				videos.add(video);
			}
			return videos;
    	}catch(Exception e){
    		System.err.println("YouTubeApiService: There was a problem fetching the video status");
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public static LiveChat getMessages(String liveChatId, String pageToken, YouTube youtubeService) {
    	System.err.println("Called chat service");
    	try {
    		YouTube.LiveChatMessages.List request = youtubeService.liveChatMessages()
    	            .list(liveChatId, "snippet,authorDetails");
	        LiveChatMessageListResponse response = request.setPageToken(pageToken).execute();
	        LiveChatStatus status = new LiveChatStatus(liveChatId, response.getNextPageToken());
	        List<ChatMessage> messages = new ArrayList<ChatMessage>();
	        ChatMessage message = null;
	        for(LiveChatMessage c : response.getItems()) {
	        	message = new ChatMessage(
	        			c.getId(), 
	        			liveChatId, 
	        			c.getSnippet().getPublishedAt().toString(), 
	        			c.getSnippet().getTextMessageDetails().getMessageText(), 
	        			c.getAuthorDetails().getDisplayName(), 
	        			c.getAuthorDetails().getIsChatOwner(), 
	        			c.getAuthorDetails().getProfileImageUrl(), 
	        			c.getAuthorDetails().getChannelId()
	        	);
	        	messages.add(message);
	        }
	        return new LiveChat(status, messages);
    	}catch(Exception e) {
    		System.err.println("YouTubeApiService: There was a problem fetching the chat messages");
    		e.printStackTrace();
    		return null;
    	}	
    }
}
