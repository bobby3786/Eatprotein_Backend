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

import com.catalog.dto.ProductImageDto;
import com.catalog.entity.ProductImage;
import com.catalog.service.IProductImageService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("/catalog/productimage")
public class ProductImageController {
	

	@Autowired
	private IProductImageService iProductImageService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createProductImage(@RequestBody ProductImageDto productImageDto){
		iProductImageService.createProductImage(productImageDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<ProductImage> fetchProductImageDetails(@RequestParam int id){
		
		ProductImage productImage = iProductImageService.fetchProductImage(id);
	return ResponseEntity.status(HttpStatus.OK).body(productImage);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateProductImageDetails(@RequestBody ProductImageDto productImageDto){
		
		boolean isUpdated = iProductImageService.updateProductImage(productImageDto);
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
	public ResponseEntity<ResponseDto> deleteProductImageDetails(@RequestParam int id){
		
		boolean isDeleted = iProductImageService.deleteProductImage(id);
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
	public ApiResponse<List<ProductImage>> getAllProducts(){
	List<ProductImage> productImage =	iProductImageService.fetchAllProductImages();
	
	return new ApiResponse<List<ProductImage>>("200", "success", productImage);
	}


}
