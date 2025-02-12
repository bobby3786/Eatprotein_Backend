package com.usercart.controller;

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
import com.usercart.dto.UserCartDto;
import com.usercart.entity.UserCart;
import com.usercart.service.IUserCartService;


@RestController
@RequestMapping("usercart/usercart")
public class UserCartController {
	

	@Autowired
	private IUserCartService iUserCartService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUserCart(@RequestBody UserCartDto userCartDto){
		iUserCartService.createUserCart(userCartDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<UserCart> fetchStoreCategoryDetails(@RequestParam("id") int id){
		
		UserCart userCart = iUserCartService.fetchUserCart(id);
	return ResponseEntity.status(HttpStatus.OK).body(userCart);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUserCartDetails(@RequestBody UserCartDto userCartDto){
		
		boolean isUpdated = iUserCartService.updateUserCart(userCartDto);
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
	public ResponseEntity<ResponseDto> deleteUserCartDetails(@RequestParam("id") int id){
		
		boolean isDeleted = iUserCartService.deleteUserCart(id);
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
	public ApiResponse<List<UserCart>> getAllUserCarts(){
	List<UserCart> userCart =	iUserCartService.fetchAllUserCarts();
	
	return new ApiResponse<List<UserCart>>("200", "success", userCart);
	}




}
