package com.example.heroku.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error to calc the total of an Order")
public class CouldNotCalcTotalPriceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

}