package com.example.heroku.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The password does not match")
public class PasswordNotMatchException extends RuntimeException {
	private static final long serialVersionUID = 1L;

}
