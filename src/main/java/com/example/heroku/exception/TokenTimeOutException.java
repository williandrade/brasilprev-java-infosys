package com.example.heroku.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Token has expired")
public class TokenTimeOutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

}