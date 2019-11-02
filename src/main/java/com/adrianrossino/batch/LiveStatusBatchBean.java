package com.adrianrossino.batch;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adrianrossino.model.ChatMessage;
import com.adrianrossino.model.LiveChat;
import com.adrianrossino.model.LiveChatStatus;
import com.adrianrossino.model.YouTubeVideo;
import com.adrianrossino.repository.ChatMessageRepository;
import com.adrianrossino.repository.LiveChatStatusRepository;
import com.adrianrossino.repository.YouTubeVideoRepository;
import com.adrianrossino.service.YouTubeApiService;
import com.google.api.services.youtube.YouTube;

@Component
public class LiveStatusBatchBean {
	
	@Autowired
	YouTubeVideoRepository ytRepo;
	
	@Autowired
	LiveChatStatusRepository chatSatusRepo;
	
	@Autowired
	ChatMessageRepository messageRepo;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@Value("${client.secret}")
	private String clientSecret;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(cron = "0 */5 * * * *")
	public void youTubeVideoStatusJob(){
		logger.info("Started new video status job");
		try {
			YouTube youtubeService = YouTubeApiService.getService(clientSecret);
			List<YouTubeVideo> videos = YouTubeApiService.getStatus(youtubeService);
			YouTubeVideo video = videos.get(0);
			Optional<YouTubeVideo> savedVideo = ytRepo.findById(video.getId());
			if(savedVideo.isPresent()){
				YouTubeVideo updateVideo = savedVideo.get();
				logger.info("Got video from DB with ID "+ updateVideo.getId());
				updateVideo = ytRepo.save(video);
				logger.info("Updated video to DB with ID "+ updateVideo.getId());
			}else{
				video = ytRepo.save(video);
				logger.info("Saved new video in DB with ID "+ video.getId());
			}
			if(video.getStatus().equals("live")) {
				String nextPageToken = "";
				Optional<LiveChatStatus> savedChat = chatSatusRepo.findById(video.getLiveChatId());
				if(savedChat.isPresent()) {
					nextPageToken = savedChat.get().getNextPageToken();
				}
				LiveChat chat = YouTubeApiService.getMessages(video.getLiveChatId(), nextPageToken, youtubeService);
				for(ChatMessage m : chat.getMessages()) {
					messageRepo.save(m);
				}
				chatSatusRepo.save(chat.getStatus());
			}
			webSocket.convertAndSend("/livestream/status", video.getStatus());
			webSocket.convertAndSend("/livestream/videos", videos);
		} catch (Exception e) {
			System.err.println("Error running YouTubeVideoStatusJob");
			e.printStackTrace();
		}
		
	}
}