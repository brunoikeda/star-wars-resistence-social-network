package com.letscode.starwarsresistencesocialnetwork.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.letscode.starwarsresistencesocialnetwork.model.ResponseError;

@ControllerAdvice
public class RebeldeExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseError> genericError(Exception e, HttpServletRequest request){
		ResponseError error = ResponseError.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(e.getMessage()).build();
		e.printStackTrace();
		return new ResponseEntity<ResponseError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ResponseError> clientError(HttpClientErrorException e, HttpServletRequest request){
		ResponseError error = ResponseError.builder().statusCode(e.getStatusCode().value()).message(e.getMessage()).build();
		e.printStackTrace();
		return new ResponseEntity<ResponseError>(error, e.getStatusCode());
	}
	
	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<ResponseError> serverError(HttpServerErrorException e, HttpServletRequest request){
		ResponseError error = ResponseError.builder().statusCode(e.getStatusCode().value()).message(e.getMessage()).build();
		e.printStackTrace();
		return new ResponseEntity<ResponseError>(error, e.getStatusCode());
	}
	
}
