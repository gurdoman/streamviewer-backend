package com.adrianrossino.auth;
import java.io.UnsupportedEncodingException;
import org.junit.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class StreamViewerTokenProviderTest {

	@Test
	public void test() {
		final String userId = "115480259359122142715";
		
		Algorithm algo = null;
		try {
			algo = Algorithm.HMAC512("Adrian1111Rossino111-StreamViewer-StreamLabs2019");
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		StreamViewerTokenProvider svtp = new StreamViewerTokenProvider();
		String token = svtp.getNewTokenFor(userId);
		System.out.println(token);
		
		DecodedJWT jwt = StreamViewerTokenProviderTest.getUserWithToken(token);
		System.out.println(jwt.getSignature());
		System.out.println(jwt.getAlgorithm());
		
		try{
			algo.verify(jwt);
		}catch (SignatureVerificationException ex){
			System.err.println("Key is not valid");
		}
		
	}
	
	public static DecodedJWT getUserWithToken(String token){
		if( token != null){
			try{
				DecodedJWT jwt = JWT.decode(token);
				return jwt;
				
			}catch (Exception ex){
				System.err.println("Invalid Signature");
			}
		}
		return null;
	}

}
