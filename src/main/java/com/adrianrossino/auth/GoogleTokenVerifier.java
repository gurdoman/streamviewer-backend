package com.adrianrossino.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Component;

import com.adrianrossino.common.InvalidTokenException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@Component
public class GoogleTokenVerifier {
	private static final HttpTransport transport = new NetHttpTransport();
	private static final JsonFactory jsonFactory = new JacksonFactory();
  	private static final String CLIENT_ID = "1029989204063-83br7bejtl1a6kjom4l7ap1er2nq6raj.apps.googleusercontent.com";
  	private static final String[] issuers = {"https://accounts.google.com", "accounts.google.com"};
  	
  	private static Payload verifyToken(String idToken) throws GeneralSecurityException, IOException, InvalidTokenException{
  		final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setIssuers(Arrays.asList(issuers)).
  				setAudience(Collections.singletonList(CLIENT_ID)).build();
  		System.out.println("Got token id: "+idToken);
  		System.out.println("Validating...");
  		GoogleIdToken googleIdToken = null;
  		
  		try{
  			googleIdToken = verifier.verify(idToken);
  		}catch(IllegalArgumentException illegalEx){
  			System.out.println("Invalid token or illegal token");
  		}
  		
  		if(googleIdToken == null){
  			throw new InvalidTokenException("Invalid token");
  		}
  		
  		return googleIdToken.getPayload();
  		
  	}
  	
  	public Payload verify(String idTokenString) throws GeneralSecurityException, IOException, InvalidTokenException {
  		return GoogleTokenVerifier.verifyToken(idTokenString);
  	}
  	
  	
  	
}
