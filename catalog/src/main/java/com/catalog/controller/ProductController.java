package com.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catalog.dto.ProductDto;
import com.catalog.entity.Product;
import com.catalog.service.IProductService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("/catalog/product")
public class ProductController {
	
	@Autowired
	private IProductService iProductService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createProduct(@RequestBody ProductDto productDto){
		iProductService.createProduct(productDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Product> fetchProductDetails(@RequestParam int id){
		
	Product product = iProductService.fetchProduct(id);
	return ResponseEntity.status(HttpStatus.OK).body(product);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateProductDetails(@RequestBody ProductDto productDto){
		
		boolean isUpdated = iProductService.updateProduct(productDto);
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
	public ResponseEntity<ResponseDto> deleteProductDetails(@RequestParam int id){
		
		boolean isDeleted = iProductService.deleteProduct(id);
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
	public ApiResponse<List<Product>> getAllProducts(){
	List<Product> product =	iProductService.fetchAllProducts();
	
	return new ApiResponse<List<Product>>("200", "success", product);
	}

	@GetMapping("/List")
	public ApiResponse<List<Product>> FetchAllProductsByFilter(@RequestParam(required = false) Integer categoryId,
			                                      @RequestParam(required = false) Integer subcategoryId,
			                                      @RequestParam(required = false) Integer brandId) {
		
		List<Product> product = iProductService.getProductsByFilter(categoryId, subcategoryId, brandId);
	     return	new ApiResponse<List<Product>>("200", "success", product);
	}
	
}
