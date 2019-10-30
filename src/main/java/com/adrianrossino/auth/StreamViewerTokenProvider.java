package com.adrianrossino.auth;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.adrianrossino.model.TokenInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class StreamViewerTokenProvider {
	
	private static final long EXPIRATION = 3456000000L;
	private static final String PREFIX = "Bearer";
	private static final String SECRET = "Adrian1111Rossino111-StreamViewer-StreamLabs2019";
	
	private static String getNewToken(String userId) {
		Algorithm algorithm = null;
		try {
			algorithm = Algorithm.HMAC512(SECRET);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    return JWT.create().withSubject(userId).withExpiresAt(new Date(System.nanoTime() + EXPIRATION)).sign(algorithm);
	}
	
	public String getNewTokenFor(String userId){
		return getNewToken(userId);
	}
	
	public static Optional<String> getUserWithToken(TokenInfo tokenInfo) throws Exception{
		final String token = tokenInfo.getTokenId();
		
		if( token != null){
			try{
				DecodedJWT jwt = JWT.decode(token.replace(PREFIX, ""));
				Algorithm algorithm = Algorithm.HMAC512(SECRET);
				algorithm.verify(jwt);
				final Instant expires = jwt.getExpiresAt().toInstant();
				if(expires.isBefore(Instant.now())){
					return Optional.empty();
				}
				return Optional.of(jwt.getSubject());
			}catch (Exception ex){
				System.err.println("Invalid Signature");
				throw ex;
			}
		}
		return Optional.empty();
	}
	

}
