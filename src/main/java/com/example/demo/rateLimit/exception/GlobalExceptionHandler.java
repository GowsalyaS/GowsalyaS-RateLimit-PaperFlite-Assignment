package com.example.demo.rateLimit.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(TooManyRequestException.class)
	public ErrorResponse handleTooManyException(TooManyRequestException tooManyEx) 
	{
		
		return new ErrorResponse(tooManyEx.getMsg(),HttpStatus.TOO_MANY_REQUESTS, LocalDateTime.now());
	}
}
