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

import com.authentication.dto.DeletedUsersDto;
import com.authentication.entity.DeletedUsers;
import com.authentication.service.IDeletedUsersService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/deletedusers")
public class DeletedUsersController {
	
	@Autowired
	private IDeletedUsersService iDeletedUsersService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createDeteldUsers(@RequestBody DeletedUsersDto deletedUsersDto){
		iDeletedUsersService.createDeletedUsers(deletedUsersDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<DeletedUsers> fetchDeletedUsersDetails(@RequestParam int id){
		
		DeletedUsers deletedUsers = iDeletedUsersService.fetchDeletedUsers(id);
	return ResponseEntity.status(HttpStatus.OK).body(deletedUsers);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateDeletedUsersDetails(@RequestBody DeletedUsersDto deletedUsersDto){
		
		boolean isUpdated = iDeletedUsersService.updateDeletedUsers(deletedUsersDto);
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
	public ResponseEntity<ResponseDto> deleteDeletedUsersDetails(@RequestParam int id){
		
		boolean isDeleted = iDeletedUsersService.deleteDeletedUsers(id);
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
	public ApiResponse<List<DeletedUsers>> getAllTags(){
	List<DeletedUsers> deletedUsers =	iDeletedUsersService.fetchAllDeletedUsers();
	
	return new ApiResponse<List<DeletedUsers>>("200", "success", deletedUsers);
	}



}
