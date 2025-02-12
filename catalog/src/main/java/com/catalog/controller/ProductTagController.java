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

import com.catalog.dto.ProductTagDto;
import com.catalog.entity.ProductTag;
import com.catalog.service.IProductTagService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("/catalog/producttag")
public class ProductTagController {
	
	@Autowired
	private IProductTagService iProductTagService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createProductTag(@RequestBody ProductTagDto productTagDto){
		iProductTagService.createProductTag(productTagDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<ProductTag> fetchProductTagDetails(@RequestParam int id){
		
		ProductTag productTag = iProductTagService.fetchProductTag(id);
	return ResponseEntity.status(HttpStatus.OK).body(productTag);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateProductTagDetails(@RequestBody ProductTagDto productTagDto){
		
		boolean isUpdated = iProductTagService.updateProductTag(productTagDto);
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
	public ResponseEntity<ResponseDto> deleteProductTagDetails(@RequestParam int id){
		
		boolean isDeleted = iProductTagService.deleteProductTag(id);
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
	public ApiResponse<List<ProductTag>> getAllProductTags(){
	List<ProductTag> productTag =	iProductTagService.fetchAllProductTags();
	
	return new ApiResponse<List<ProductTag>>("200", "success", productTag);
	}




}
