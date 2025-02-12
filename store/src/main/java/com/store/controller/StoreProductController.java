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
import com.store.dto.StoreProductDto;
import com.store.entity.StoreProduct;
import com.store.service.IStoreProductService;


@RestController
@RequestMapping("store/storeproduct")
public class StoreProductController {
	

	@Autowired
	private IStoreProductService iStoreProductService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createStoreProduct(@RequestBody StoreProductDto storeProductDto){
		iStoreProductService.createStoreProduct(storeProductDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<StoreProduct> fetchStoreProductDetails(@RequestParam("id") String id){
		
		StoreProduct storeProduct = iStoreProductService.fetchStoreProduct(id);
	return ResponseEntity.status(HttpStatus.OK).body(storeProduct);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateStoreProductDetails(@RequestBody StoreProductDto storeProductDto){
		
		boolean isUpdated = iStoreProductService.updateStoreProduct(storeProductDto);
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
	public ResponseEntity<ResponseDto> deleteStoreProductDetails(@RequestParam("id") String id){
		
		boolean isDeleted = iStoreProductService.deleteStoreProduct(id);
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
	public ApiResponse<List<StoreProduct>> getAllStoreProducts(){
	List<StoreProduct> storeProduct =	iStoreProductService.fetchAllStoreProducts();
	
	return new ApiResponse<List<StoreProduct>>("200", "success", storeProduct);
	}



}
