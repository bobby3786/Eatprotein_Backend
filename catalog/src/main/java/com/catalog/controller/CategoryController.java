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

import com.catalog.dto.CategoryDto;
import com.catalog.entity.Category;
import com.catalog.service.ICategoryService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("/catalog/category")
public class CategoryController {
    
	@Autowired
	private ICategoryService iCategoryService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCategory(@RequestBody CategoryDto categoryDto){
		iCategoryService.createCategory(categoryDto);
		return ResponseEntity
		                   .status(HttpStatus.CREATED)
		                   .body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<Category> fetchCategoryDetails(@RequestParam int id){
		Category category = iCategoryService.fetchCategory(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateCategoryDetails(@RequestBody CategoryDto categoryDto){
	boolean isUpdated =	iCategoryService.updateCategory(categoryDto);
	
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
	public ResponseEntity<ResponseDto> deleteCategoryDetails(@RequestParam int id){
		
		boolean isDeleted = iCategoryService.deleteCategory(id);
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
	public ApiResponse<List<Category>> getAllProducts(){
	List<Category> category =	iCategoryService.fetchAllCategories();
	
	return new ApiResponse<List<Category>>("200", "success", category);
	}

}
