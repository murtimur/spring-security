package com.muratyildirim.spring_security.user.validation;

import com.muratyildirim.spring_security.user.User;
import com.muratyildirim.spring_security.user.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	// @Autowired
	UserService userService;

	public UniqueUsernameValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		User inDB = userService.getUser(username);
		return inDB == null;
	}

}