package com.catalog.controller;

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

import com.catalog.dto.UserRecentViewDto;
import com.catalog.entity.UserRecentView;
import com.catalog.service.IUserRecentViewService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("catalog/userrecentview")
public class UserRecentViewController {

	@Autowired
	private IUserRecentViewService iUserRecentViewService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUserRecentView(@RequestBody UserRecentViewDto userRecentViewDto){
		iUserRecentViewService.createUserRecentView(userRecentViewDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<UserRecentView> fetchUserRecentViewDetails(@RequestParam int id){
		
		UserRecentView userRecentView = iUserRecentViewService.fetchUserRecentView(id);
	return ResponseEntity.status(HttpStatus.OK).body(userRecentView);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUserRecentViewDetails(@RequestBody UserRecentViewDto userRecentViewDto){
		
		boolean isUpdated = iUserRecentViewService.updateUserRecentView(userRecentViewDto);
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
	public ResponseEntity<ResponseDto> deleteUserRecentViewDetails(@RequestParam int id){
		
		boolean isDeleted = iUserRecentViewService.deleteUserRecentView(id);
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
	public ApiResponse<List<UserRecentView>> getAllUserRecentViews(){
	List<UserRecentView> userRecentView =	iUserRecentViewService.fetchAllUserRecentViews();
	
	return new ApiResponse<List<UserRecentView>>("200", "success", userRecentView);
	}


	
}
