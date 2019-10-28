package com.adrianrossino.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import com.adrianrossino.model.Users;

public interface UserRegisterService {
	Users saveUser(Payload payload);
}
