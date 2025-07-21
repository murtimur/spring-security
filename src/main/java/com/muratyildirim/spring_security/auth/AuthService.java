package com.muratyildirim.spring_security.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muratyildirim.spring_security.auth.dto.AuthResponse;
import com.muratyildirim.spring_security.auth.dto.Credentials;
import com.muratyildirim.spring_security.auth.exception.AuthenticationException;
import com.muratyildirim.spring_security.auth.token.Token;
import com.muratyildirim.spring_security.auth.token.TokenService;
import com.muratyildirim.spring_security.user.User;
import com.muratyildirim.spring_security.user.UserService;
import com.muratyildirim.spring_security.user.dto.UserDTO;

@Service
public class AuthService {

	UserService userService;

	PasswordEncoder passwordEncoder;

	TokenService tokenService;
	
	public AuthService(UserService userService, PasswordEncoder passwordEncoder, TokenService tokenService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
	}

	public AuthResponse authenticate(Credentials creds) {
		User inDB = userService.getUser(creds.username());
		if (inDB == null)
			throw new AuthenticationException();
		if (!passwordEncoder.matches(creds.password(), inDB.getPassword()))
			throw new AuthenticationException();
		Token token = tokenService.createToken(inDB, creds);
		AuthResponse auth = new AuthResponse();
		auth.setToken(token);
		auth.setUser(new UserDTO(inDB));
		return auth;
	}

	public void logout(String authorizationHeader) {
		tokenService.logout(authorizationHeader);
	}

}
