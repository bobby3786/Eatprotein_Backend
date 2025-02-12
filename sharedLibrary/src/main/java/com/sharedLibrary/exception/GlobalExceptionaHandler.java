package com.sharedLibrary.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.sharedLibrary.dto.ErrorResponseDto;



@ControllerAdvice
public class GlobalExceptionaHandler {
	
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleBannerAlredyExistsException(AlreadyExistsException exception,WebRequest webRequest){
		
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				webRequest.getDescription(false),
				HttpStatus.BAD_REQUEST,
				exception.getMessage(),
				LocalDateTime.now()
				);
		
		return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException1(ResourceNotFoundException1 exception,WebRequest webRequest){
		
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				webRequest.getDescription(false),
				HttpStatus.NO_CONTENT,
				exception.getMessage(),
				LocalDateTime.now()
				);
		
		return new ResponseEntity<>(errorResponseDto,HttpStatus.NO_CONTENT);
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,WebRequest webRequest){
		
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				webRequest.getDescription(false),
				HttpStatus.NO_CONTENT,
				exception.getMessage(),
				LocalDateTime.now()
				);
		
		return new ResponseEntity<>(errorResponseDto,HttpStatus.NO_CONTENT);
		
	}

}
