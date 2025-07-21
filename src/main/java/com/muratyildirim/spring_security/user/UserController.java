package com.muratyildirim.spring_security.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.muratyildirim.spring_security.shared.GenericMessage;
import com.muratyildirim.spring_security.user.dto.UserCreateDTO;

import jakarta.validation.Valid;

@RestController
public class UserController {

	UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/api/1.0/users")
	GenericMessage save(@Valid @RequestBody UserCreateDTO user) {
		userService.save(user.toUser());
		return new GenericMessage("User created.");
	}

}
