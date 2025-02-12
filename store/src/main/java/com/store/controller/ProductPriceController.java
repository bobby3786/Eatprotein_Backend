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
import com.store.dto.ProductPriceDto;
import com.store.entity.ProductPrice;
import com.store.service.IProductPriceService;

@RestController
@RequestMapping("/store/productprice")
public class ProductPriceController {
	
	@Autowired
	private IProductPriceService iProductPriceService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createProductPrice(@RequestBody ProductPriceDto productPriceDto){
		iProductPriceService.createProductPrice(productPriceDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<ProductPrice> fetchProductPriceDetails(@RequestParam String id){
		
		ProductPrice productPrice = iProductPriceService.fetchProductPrice(id);
	return ResponseEntity.status(HttpStatus.OK).body(productPrice);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateProductPriceDetails(@RequestBody ProductPriceDto productPriceDto){
		
		boolean isUpdated = iProductPriceService.updateProductPrice(productPriceDto);
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
	public ResponseEntity<ResponseDto> deleteProductPriceDetails(@RequestParam String id){
		
		boolean isDeleted = iProductPriceService.deleteProductPrice(id);
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
	public ApiResponse<List<ProductPrice>> getAllProductPrices(){
	List<ProductPrice> productPrice =	iProductPriceService.fetchAllProductPrices();
	
	return new ApiResponse<List<ProductPrice>>("200", "success", productPrice);
	}



}
