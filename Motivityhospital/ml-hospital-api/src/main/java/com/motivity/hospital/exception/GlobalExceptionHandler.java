package com.motivity.hospital.exception;

import java.time.LocalDateTime;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.motivity.hospital.controller.DoctorController;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> userNotFound(Exception ex,WebRequest request)
	{
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		org.slf4j.Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.info(errors+" "+HttpStatus.NOT_FOUND.value()+" "+ex.getMessage());
		errors.setError(ex.getMessage());
		return new  ResponseEntity<CustomErrorResponse>(errors,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(NoIdPresentsException.class)
	public ResponseEntity<CustomErrorResponse> idNotPresent(Exception ex,WebRequest request)
	{
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setStatus(HttpStatus.NO_CONTENT.value());
		org.slf4j.Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.info(errors+" "+HttpStatus.NO_CONTENT.value()+" "+ex.getMessage());
		errors.setError(ex.getMessage());
		return new  ResponseEntity<CustomErrorResponse>(errors,HttpStatus.NO_CONTENT);
	}
	@ExceptionHandler(PasswordMisMatchException.class)
	public ResponseEntity<CustomErrorResponse> passwordMismatch(Exception ex,WebRequest request)
	{
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setStatus(HttpStatus.UNAUTHORIZED.value());
		org.slf4j.Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.info(errors+" "+HttpStatus.UNAUTHORIZED.value()+" "+ex.getMessage());
		errors.setError(ex.getMessage());
		return new  ResponseEntity<CustomErrorResponse>(errors,HttpStatus.UNAUTHORIZED);
	}
	
	

}
