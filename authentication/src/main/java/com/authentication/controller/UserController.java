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

import com.authentication.dto.UserDto;
import com.authentication.entity.User;
import com.authentication.service.IUserService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/user")
public class UserController {

	@Autowired
	private IUserService iUserService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUser(@RequestBody UserDto userDto){
		iUserService.createUser(userDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<User> fetchRoleDetails(@RequestParam int id){
		
		User user = iUserService.fetchUser(id);
	return ResponseEntity.status(HttpStatus.OK).body(user);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUserDetails(@RequestBody UserDto userDto){
		
		boolean isUpdated = iUserService.updateUser(userDto);
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
	public ResponseEntity<ResponseDto> deleteUserDetails(@RequestParam int id){
		
		boolean isDeleted = iUserService.deleteUser(id);
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
	public ApiResponse<List<User>> getAllTags(){
	List<User> user =	iUserService.fetchAllUsers();
	
	return new ApiResponse<List<User>>("200", "success", user);
	}



	
}
