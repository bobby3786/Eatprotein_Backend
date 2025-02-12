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

import com.authentication.dto.UserDeletedDto;
import com.authentication.entity.UserDeleted;
import com.authentication.service.IUserDeletedService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/userdeleted")
public class UserDeletedController {

	@Autowired
	private IUserDeletedService iUserDeletedService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUserDeleted(@RequestBody UserDeletedDto userDeletedDto){
		iUserDeletedService.createUserDeleted(userDeletedDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<UserDeleted> fetchUserDeletedDetails(@RequestParam int id){
		
		UserDeleted userDeleted = iUserDeletedService.fetchUserDeleted(id);
	return ResponseEntity.status(HttpStatus.OK).body(userDeleted);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateuserDeletedDetails(@RequestBody UserDeletedDto userDeletedDto){
		
		boolean isUpdated = iUserDeletedService.updateUserDeleted(userDeletedDto);
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
	public ResponseEntity<ResponseDto> deleteUserDeletedDetails(@RequestParam int id){
		
		boolean isDeleted = iUserDeletedService.deleteUserDeleted(id);
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
	public ApiResponse<List<UserDeleted>> getAllUserDeleteds(){
	List<UserDeleted> userDeleted =	iUserDeletedService.fetchAllUsersDeleted();
	
	return new ApiResponse<List<UserDeleted>>("200", "success", userDeleted);
	}


	
}
