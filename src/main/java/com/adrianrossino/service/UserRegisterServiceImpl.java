package com.adrianrossino.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrianrossino.auth.StreamViewerTokenProvider;
import com.adrianrossino.model.Users;
import com.adrianrossino.repository.UsersRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@Component
public class UserRegisterServiceImpl implements UserRegisterService{

	@Autowired
	UsersRepository userRepo;
	
	@Override
	public Users saveUser(Payload payload) {
		Optional<Users> user = null;
		Users newUser = null;
		try{
			String userId = payload.getSubject();
			user = userRepo.findById(userId);
			StreamViewerTokenProvider svtp = new StreamViewerTokenProvider();
			String token = svtp.getNewTokenFor(userId.toString());
			
			if(user.isPresent()){
				newUser = user.get();
				newUser.setToken(token);
			}else{
				String email = payload.getEmail();
				String familyName = (String)payload.get("family_name");
				String picture = (String)payload.get("picture");
				String name = (String)payload.get("given_name");
				newUser = new Users(userId, name, token, email, picture, familyName);
			}
			
			newUser = userRepo.save(newUser);
		}catch(Exception ex){
			System.err.println("There was a problem creating or updating the user: "+ex);
			ex.printStackTrace();
		}
		
		return newUser;
	}

}
