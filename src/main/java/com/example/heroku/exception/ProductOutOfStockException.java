package com.example.heroku.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The number required is more that what we have in stock")
public class ProductOutOfStockException extends RuntimeException {
	private static final long serialVersionUID = 1L;

}
