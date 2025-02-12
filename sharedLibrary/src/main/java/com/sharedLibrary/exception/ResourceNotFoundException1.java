package com.sharedLibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ResourceNotFoundException1 extends RuntimeException{
	
	public ResourceNotFoundException1(String resourceName,String fieldName,String id) {
		
		super (String.format("%s not found with the given input data %s:%s",resourceName,fieldName,id));
	}

}
