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

import com.catalog.dto.NewUserCouponUsageDto;
import com.catalog.entity.NewUserCouponUsage;
import com.catalog.service.INewUserCouponUsageService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("catalog/nucu")
public class NewUserCouponUsageController {
	
	@Autowired
	private INewUserCouponUsageService iNewUserCouponUsageService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createNewUserCouponUsage(@RequestBody NewUserCouponUsageDto newUserCouponUsageDto){
		iNewUserCouponUsageService.createNewUserCouponUsage(newUserCouponUsageDto);
		return ResponseEntity
		                   .status(HttpStatus.CREATED)
		                   .body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<NewUserCouponUsage> fetchNewUserCouponUsageDetails(@RequestParam int id){
		NewUserCouponUsage newUserCouponUsage = iNewUserCouponUsageService.fetchNewUserCouponUsage(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(newUserCouponUsage);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateNewUserCouponUsage(@RequestBody NewUserCouponUsageDto newUserCouponUsageDto){
	boolean isUpdated =	iNewUserCouponUsageService.updateNewUserCouponUsage(newUserCouponUsageDto);
	
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
	public ResponseEntity<ResponseDto> deleteNewUserCouponUsageDetails(@RequestParam int id){
		
		boolean isDeleted = iNewUserCouponUsageService.deleteNewUserCouponUsage(id);
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
	public ApiResponse<List<NewUserCouponUsage>> getAllNewUserCouponUsages(){
	List<NewUserCouponUsage> newUserCouponUsage =	iNewUserCouponUsageService.fetchAllNewUserCouponUsages();
	
	return new ApiResponse<List<NewUserCouponUsage>>("200", "success", newUserCouponUsage);
	}



}
