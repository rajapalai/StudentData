package com.student.details.exceptionhandle;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleGlobalException extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ErrorMessage.class)
	public ResponseEntity<Object> handleInput(ErrorMessage error) {
		HttpStatus badrequest = HttpStatus.BAD_REQUEST;
		ApiException exception = new ApiException(error.getMessage(), badrequest);
		return new ResponseEntity<Object>(exception, badrequest);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		HttpStatus status1 = HttpStatus.NOT_FOUND;
		ApiException exception = new ApiException("Something went wrong in http method, please enter a valid url",status1);
		return new ResponseEntity<Object>(exception,status1);
	}
	
}
