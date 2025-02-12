package com.authentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.FcmAuthTokenDto;
import com.authentication.entity.FcmAuthToken;
import com.authentication.service.IFcmAuthTokenService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/fat")
public class FcmAuthTokenController {
	
	@Autowired
	private IFcmAuthTokenService iFcmAuthTokenService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createFcmAuthToken(@RequestBody FcmAuthTokenDto fcmAuthTokenDto){
		iFcmAuthTokenService.createFcmAuthToken(fcmAuthTokenDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<FcmAuthToken> fetchFcmAuthTokenDetails(@RequestParam int id){
		
		FcmAuthToken fcmAuthToken = iFcmAuthTokenService.fetchFcmAuthToken(id);
	return ResponseEntity.status(HttpStatus.OK).body(fcmAuthToken);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateFcmAuthTokenDetails(@RequestBody FcmAuthTokenDto fcmAuthTokenDto){
		
		boolean isUpdated = iFcmAuthTokenService.updateFcmAuthToken(fcmAuthTokenDto);
		if(isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
			
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500, Constants.MESSAGE_500));
			
		}
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteFcmAuthTokenDetails(@RequestParam int id){
		
		boolean isDeleted = iFcmAuthTokenService.deleteFcmAuthToken(id);
		if(isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
			
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500, Constants.MESSAGE_500));
			
		}
		
	}

	@GetMapping("/All")
	public ApiResponse<List<FcmAuthToken>> getAllFcmAuthTokens(){
	List<FcmAuthToken> fcmAuthToken =	iFcmAuthTokenService.fetchAllFcmAuthTokens();
	
	return new ApiResponse<List<FcmAuthToken>>("200", "success", fcmAuthToken);
	}




}
