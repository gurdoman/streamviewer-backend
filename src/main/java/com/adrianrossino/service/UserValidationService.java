package com.adrianrossino.service;

import com.adrianrossino.model.TokenInfo;
import com.adrianrossino.model.Users;

public interface UserValidationService {
	Users validateUser(TokenInfo tokenInfo) throws Exception;
}
