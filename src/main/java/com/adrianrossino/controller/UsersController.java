package com.adrianrossino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adrianrossino.auth.GoogleTokenVerifier;
import com.adrianrossino.model.TokenInfo;
import com.adrianrossino.model.Users;
import com.adrianrossino.repository.UsersRepository;
import com.adrianrossino.service.UserRegisterService;
import com.adrianrossino.service.UserValidationService;
import com.adrianrossino.service.YouTubeApiService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.LiveBroadcastListResponse;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("users")
public class UsersController {
	
	@Autowired
	UsersRepository userRepo;
	
	@Autowired
	UserRegisterService userReg;
	
	@Autowired
	UserValidationService userValid;
	
	@Value("${client.secret}")
	private String clientSecret;
	
	
	@GetMapping
	public LiveBroadcastListResponse getYoutube(){
		try {
			YouTube youtubeService = YouTubeApiService.getService(clientSecret);
			YouTube.LiveBroadcasts.List request = youtubeService.liveBroadcasts().list("id,snippet,contentDetails,status");
			LiveBroadcastListResponse response = request.setBroadcastType("all").setMine(true).execute();
		    return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping
	public Users setUsers(@RequestBody TokenInfo token){
		System.out.println(token.getSource());
		TokenInfo returnToken = new TokenInfo();
		Users user = null;
		try{
			if(token.getSource().equals("google")){
				GoogleTokenVerifier google = new GoogleTokenVerifier();
				Payload payload = google.verify(token.getTokenId());
				user = userReg.saveUser(payload);
				returnToken.setSource("streamviewer");
				returnToken.setTokenId(user.getToken());
			}else if(token.getSource().equals("streamviewer")){
				user = userValid.validateUser(token);
			}else{
				throw new Exception("Source not valid");
			}
			System.out.println(user.getUser_id());
		}catch (Exception e){
			System.err.println("Error setting the user " +e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		return user;
	}

}
