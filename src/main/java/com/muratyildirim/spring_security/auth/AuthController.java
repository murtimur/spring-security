package com.muratyildirim.spring_security.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.muratyildirim.spring_security.auth.dto.AuthResponse;
import com.muratyildirim.spring_security.auth.dto.Credentials;
import com.muratyildirim.spring_security.shared.GenericMessage;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/api/1.0/auth")
	ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials creds) {
		var authResponse = authService.authenticate(creds);
		var cookie = ResponseCookie.from("murtimur-access-token", authResponse.getToken().getToken()).path("/")
				.httpOnly(true).build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
	}

	@PostMapping("/api/1.0/logout")
	ResponseEntity<GenericMessage> handleLogout(
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
			@CookieValue(name = "murtimur-access-token", required = false) String cookieValue) {
		var tokenWithPrefix = authorizationHeader;
		if (cookieValue != null) {
			tokenWithPrefix = "AnyPrefix " + cookieValue;
		}
		authService.logout(tokenWithPrefix);
		var cookie = ResponseCookie.from("murtimur-access-token", "").path("/").maxAge(0).httpOnly(true).build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new GenericMessage("Logout success"));
	}

}
