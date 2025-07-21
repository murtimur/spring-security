package com.muratyildirim.spring_security.user.dto;

import com.muratyildirim.spring_security.user.User;
import com.muratyildirim.spring_security.user.validation.UniqueUsername;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(@NotBlank @UniqueUsername String username, @NotBlank String password) {

	public User toUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}
	
}
