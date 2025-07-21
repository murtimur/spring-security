package com.muratyildirim.spring_security.auth.token;

import java.util.Base64;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muratyildirim.spring_security.auth.dto.Credentials;
import com.muratyildirim.spring_security.user.User;
import com.muratyildirim.spring_security.user.UserService;

@Service
@ConditionalOnProperty(name = "murtimur.token-type", havingValue = "basic")
public class BasicAuthTokenService implements TokenService {

	UserService userService;

	PasswordEncoder passwordEncoder;

	public BasicAuthTokenService(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Token createToken(User user, Credentials creds) {
		// TODO Auto-generated method stub
		String usernameColumnPassword = creds.username() + ":" + creds.password();
		String token = Base64.getEncoder().encodeToString(usernameColumnPassword.getBytes());
		return new Token("Basic", token);
	}

	@Override
	public User verifyToken(String authoriationHeader) {
		// TODO Auto-generated method stub
		if(authoriationHeader == null) return null;
		var base64Encoded = authoriationHeader.split("Basic ")[1];
		var decoded = new String(Base64.getDecoder().decode(base64Encoded));
		var credentials = decoded.split(":");
		String username = credentials[0];
		String password = credentials[1];
		User inDB = userService.getUser(username);
		if(inDB == null) return null;
		if(!passwordEncoder.matches(password, inDB.getPassword())) return null;
		return inDB;
	}

	@Override
	public void logout(String authorizationHeader) {
		// TODO Auto-generated method stub

	}

}
