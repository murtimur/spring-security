package com.muratyildirim.spring_security.auth.token;

import com.muratyildirim.spring_security.auth.dto.Credentials;
import com.muratyildirim.spring_security.user.User;

public interface TokenService {

	public Token createToken(User user, Credentials creds);

	public User verifyToken(String authoriationHeader);

	public void logout(String authorizationHeader);

}
