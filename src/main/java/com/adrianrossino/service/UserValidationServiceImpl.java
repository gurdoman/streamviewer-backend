package com.adrianrossino.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrianrossino.auth.StreamViewerTokenProvider;
import com.adrianrossino.model.TokenInfo;
import com.adrianrossino.model.Users;
import com.adrianrossino.repository.UsersRepository;

@Component
public class UserValidationServiceImpl implements UserValidationService{

	@Autowired
	UsersRepository userRepo;
	
	@Override
	public Users validateUser(TokenInfo tokenInfo) throws Exception {
		Users thisUser = null;		
		try{
			Optional<String> userId = StreamViewerTokenProvider.getUserWithToken(tokenInfo);		
			if(userId.isPresent()){
				Optional<Users> user = userRepo.findById(userId.get());
				thisUser = user.get();
			}
		}catch(Exception e){
			System.err.println("User not found or invalid for token: " + tokenInfo.getTokenId());
			throw new Exception("Invalid Token");
		}	
		return thisUser;
	}

}
