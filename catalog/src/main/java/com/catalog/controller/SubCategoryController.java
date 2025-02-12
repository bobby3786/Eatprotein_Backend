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

import com.catalog.dto.SubCategoryDto;
import com.catalog.entity.SubCategory;
import com.catalog.service.ISubCategoryService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("/catalog/subcategory")
public class SubCategoryController {

	
	 
		@Autowired
		private ISubCategoryService iSubCategoryService;
		
		@PostMapping("/create")
		public ResponseEntity<ResponseDto> createSubCategory(@RequestBody SubCategoryDto subCategoryDto){
			iSubCategoryService.createSubCategory(subCategoryDto);
			return ResponseEntity
			                   .status(HttpStatus.CREATED)
			                   .body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
		}
		
		@GetMapping("/fetch")
		public ResponseEntity<SubCategory> fetchSubCategoryDetails(@RequestParam int id){
			SubCategory subCategory = iSubCategoryService.fetchSubCategory(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(subCategory);
		}
		
		@PutMapping("/update")
		public ResponseEntity<ResponseDto> updateSubCategoryDetails(@RequestBody SubCategoryDto subCategoryDto){
		boolean isUpdated =	iSubCategoryService.updateSubCategory(subCategoryDto);
		
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
		public ResponseEntity<ResponseDto> deleteSubCategoryDetails(@RequestParam int id){
			
			boolean isDeleted = iSubCategoryService.deleteSubCategory(id);
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
		public ApiResponse<List<SubCategory>> getAllProducts(){
		List<SubCategory> subCategory =	iSubCategoryService.fetchAllSubCategories();
		
		return new ApiResponse<List<SubCategory>>("200", "success", subCategory);
		}

}
