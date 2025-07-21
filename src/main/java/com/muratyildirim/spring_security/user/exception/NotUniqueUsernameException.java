package com.muratyildirim.spring_security.user.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import com.muratyildirim.spring_security.shared.Messages;

public class NotUniqueUsernameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotUniqueUsernameException() {
		super(Messages.getMessageForLocale("murtimur.error.validation", LocaleContextHolder.getLocale()));
	}

	public Map<String, String> getValidationErrors() {
		return Collections.singletonMap("username", Messages
				.getMessageForLocale("murtimur.constraint.username.notunique", LocaleContextHolder.getLocale()));
	}

}