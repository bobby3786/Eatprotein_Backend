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
import com.store.dto.StoreCategoryDto;
import com.store.entity.StoreCategory;
import com.store.service.IStoreCategoryService;

@RestController
@RequestMapping("store/storecategory")
public class StoreCategoryController {
	

	@Autowired
	private IStoreCategoryService iStoreCategoryService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createStoreCategory(@RequestBody StoreCategoryDto storeCategoryDto){
		iStoreCategoryService.createStoreCategory(storeCategoryDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<StoreCategory> fetchStoreCategoryDetails(@RequestParam("id") String id){
		
		StoreCategory storeCategory = iStoreCategoryService.fetchStoreCategory(id);
	return ResponseEntity.status(HttpStatus.OK).body(storeCategory);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateStoreCategoryDetails(@RequestBody StoreCategoryDto storeCategoryDto){
		
		boolean isUpdated = iStoreCategoryService.updateStoreCategory(storeCategoryDto);
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
	public ResponseEntity<ResponseDto> deleteStoreCategoryDetails(@RequestParam("id") String id){
		
		boolean isDeleted = iStoreCategoryService.deleteStoreCategory(id);
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
	public ApiResponse<List<StoreCategory>> getAllStoreCategories(){
	List<StoreCategory> storeCategory =	iStoreCategoryService.fetchAllStoreCategories();
	
	return new ApiResponse<List<StoreCategory>>("200", "success", storeCategory);
	}



}
