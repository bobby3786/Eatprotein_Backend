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

import com.catalog.dto.ProductRatingDto;
import com.catalog.entity.ProductRating;
import com.catalog.service.IProductRatingService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("/catalog/productrating")
public class ProductRatingController {
	@Autowired
	private IProductRatingService iProductRatingService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createProductRating(@RequestBody ProductRatingDto productRatingDto){
		iProductRatingService.createProductRating(productRatingDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<ProductRating> fetchProductRatingDetails(@RequestParam int id){
		
		ProductRating productRating = iProductRatingService.fetchProductRating(id);
	return ResponseEntity.status(HttpStatus.OK).body(productRating);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateProductRatingDetails(@RequestBody ProductRatingDto productRatingDto){
		
		boolean isUpdated = iProductRatingService.updateProductRating(productRatingDto);
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
	public ResponseEntity<ResponseDto> deleteProductRatingDetails(@RequestParam int id){
		
		boolean isDeleted = iProductRatingService.deleteProductRating(id);
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
	public ApiResponse<List<ProductRating>> getAllProductRatings(){
	List<ProductRating> productRating =	iProductRatingService.fetchAllProductRatings();
	
	return new ApiResponse<List<ProductRating>>("200", "success", productRating);
	}




}
