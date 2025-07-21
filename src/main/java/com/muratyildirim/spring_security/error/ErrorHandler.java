package com.muratyildirim.spring_security.error;

import java.util.stream.Collectors;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.muratyildirim.spring_security.auth.exception.AuthenticationException;
import com.muratyildirim.spring_security.shared.Messages;
import com.muratyildirim.spring_security.user.exception.NotUniqueUsernameException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler({ MethodArgumentNotValidException.class, NotUniqueUsernameException.class,
			AuthenticationException.class })
	ResponseEntity<ApiError> handleError(Exception exception, HttpServletRequest request) {
		ApiError apiError = new ApiError();
		apiError.setPath(request.getRequestURI());
		apiError.setMessage(exception.getMessage());
		if (exception instanceof MethodArgumentNotValidException) {
			String message = Messages.getMessageForLocale("murtimur.error.validation", LocaleContextHolder.getLocale());
			apiError.setMessage(message);
			apiError.setStatus(400);
			var validationErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()
					.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
							(existing, replacing) -> existing));
			apiError.setValidationErrors(validationErrors);
		} else if (exception instanceof NotUniqueUsernameException) {
			apiError.setStatus(400);
			apiError.setValidationErrors(((NotUniqueUsernameException) exception).getValidationErrors());
		} else if (exception instanceof AuthenticationException) {
			apiError.setStatus(401);
		}
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}

}
