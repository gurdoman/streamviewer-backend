package com.adrianrossino.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrianrossino.auth.GoogleTokenVerifier;
import com.adrianrossino.model.TokenInfo;
import com.adrianrossino.model.Users;
import com.adrianrossino.repository.UsersRepository;
import com.adrianrossino.service.UserRegisterService;
import com.adrianrossino.service.UserValidationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@RestController
@RequestMapping("users")
public class UsersResource {
	
	@Autowired
	UsersRepository userRepo;
	
	@Autowired
	UserRegisterService userReg;
	
	@Autowired
	UserValidationService userValid;
	
	@PostMapping
	public Users setUsers(@RequestBody TokenInfo token){
		System.out.println("Looked for setUsers");
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
			}
		}catch (Exception e){
			System.err.println("Error setting the user " +e.getMessage());
		}
		
		return user;
	}

}
