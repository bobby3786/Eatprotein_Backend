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

import com.authentication.dto.UserPromocodeDto;
import com.authentication.entity.UserPromocode;
import com.authentication.service.IUserPromocodeService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/userpromocode")
public class UserPromocodeController {
	
	@Autowired
	private IUserPromocodeService iUserPromocodeService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUserPromocode(@RequestBody UserPromocodeDto userPromocodeDto){
		iUserPromocodeService.createUserPromocode(userPromocodeDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<UserPromocode> fetchUserPromocodeDetails(@RequestParam int id){
		
		UserPromocode userPromocode = iUserPromocodeService.fetchUserPromocode(id);
	return ResponseEntity.status(HttpStatus.OK).body(userPromocode);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUserPromocodeDetails(@RequestBody UserPromocodeDto userPromocodeDto){
		
		boolean isUpdated = iUserPromocodeService.updateUserPromocode(userPromocodeDto);
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
	public ResponseEntity<ResponseDto> deleteUserPromocodeDetails(@RequestParam int id){
		
		boolean isDeleted = iUserPromocodeService.deleteUserPromocode(id);
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
	public ApiResponse<List<UserPromocode>> getAllTags(){
	List<UserPromocode> userPromocode =	iUserPromocodeService.fetchAllUserPromocodes();
	
	return new ApiResponse<List<UserPromocode>>("200", "success", userPromocode);
	}




}
