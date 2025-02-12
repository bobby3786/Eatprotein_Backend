package com.sharedLibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String resourceName,String fieldName,int id) {
		
		super (String.format("%s not found with the given input data %s:%s",resourceName,fieldName,id));
	}

}

