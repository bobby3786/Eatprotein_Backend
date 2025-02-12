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

import com.catalog.dto.BrandDto;
import com.catalog.entity.Brand;
import com.catalog.service.IBrandService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("catalog/brand")
public class BrandController {
	
	@Autowired
	private IBrandService iBrandService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createBrand(@RequestBody BrandDto brandDto){
		iBrandService.createBrand(brandDto);
		return ResponseEntity
		                   .status(HttpStatus.CREATED)
		                   .body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<Brand> fetchBrandDetails(@RequestParam int id){
		Brand brand = iBrandService.fetchBrand(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(brand);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateBrand(@RequestBody BrandDto brandDto){
	boolean isUpdated =	iBrandService.updateBrand(brandDto);
	
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
	public ResponseEntity<ResponseDto> deleteBrandDetails(@RequestParam int id){
		
		boolean isDeleted = iBrandService.deleteBrand(id);
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
	public ApiResponse<List<Brand>> getAllBrands(){
	List<Brand> brand =	iBrandService.fetchAllBrands();
	
	return new ApiResponse<List<Brand>>("200", "success", brand);
	}


}
