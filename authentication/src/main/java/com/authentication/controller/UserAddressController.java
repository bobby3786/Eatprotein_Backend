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

import com.authentication.dto.UserAddressDto;
import com.authentication.entity.UserAddress;
import com.authentication.service.IUserAddressService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/useraddress")
public class UserAddressController {
	
	@Autowired
	private IUserAddressService iUserAddressService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUserAddress(@RequestBody UserAddressDto userAddressDto){
		iUserAddressService.createUserAddress(userAddressDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<UserAddress> fetchRoleDetails(@RequestParam int id){
		
		UserAddress userAddress = iUserAddressService.fetchUserAddress(id);
	return ResponseEntity.status(HttpStatus.OK).body(userAddress);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUserAddressDetails(@RequestBody UserAddressDto userAddressDto){
		
		boolean isUpdated = iUserAddressService.updateUserAddress(userAddressDto);
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
	public ResponseEntity<ResponseDto> deleteUserAddressDetails(@RequestParam int id){
		
		boolean isDeleted = iUserAddressService.deleteUserAddress(id);
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
	public ApiResponse<List<UserAddress>> getAllTags(){
	List<UserAddress> userAddress =	iUserAddressService.fetchAllUserAddresses();
	
	return new ApiResponse<List<UserAddress>>("200", "success", userAddress);
	}




}
