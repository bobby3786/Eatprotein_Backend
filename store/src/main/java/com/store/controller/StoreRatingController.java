package com.store.controller;

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

import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;
import com.store.dto.StoreRatingDto;
import com.store.entity.StoreRating;
import com.store.service.IStoreRatingService;

@RestController
@RequestMapping("store/storerating")
public class StoreRatingController {
	
	@Autowired
	private IStoreRatingService iStoreRatingService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createStoreRating(@RequestBody StoreRatingDto storeRatingDto){
		iStoreRatingService.createStoreRating(storeRatingDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<StoreRating> fetchStoreRatingDetails(@RequestParam("id") String id){
		
		StoreRating storeRating = iStoreRatingService.fetchStoreRating(id);
	return ResponseEntity.status(HttpStatus.OK).body(storeRating);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateStoreRatingDetails(@RequestBody StoreRatingDto storeRatingDto){
		
		boolean isUpdated = iStoreRatingService.updateStoreRating(storeRatingDto);
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
	public ResponseEntity<ResponseDto> deleteStoreDetails(@RequestParam("id") String id){
		
		boolean isDeleted = iStoreRatingService.deleteStoreRating(id);
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
	public ApiResponse<List<StoreRating>> getAllStoreRatings(){
	List<StoreRating> storeRating =	iStoreRatingService.fetchAllStoreRatings();
	
	return new ApiResponse<List<StoreRating>>("200", "success", storeRating);
	}



}
