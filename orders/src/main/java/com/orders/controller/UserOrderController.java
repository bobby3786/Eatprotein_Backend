package com.orders.controller;

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

import com.orders.dto.UserOrderDto;
import com.orders.entity.UserOrder;
import com.orders.service.IUserOrderService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("orders/userorder")
public class UserOrderController {
	

	@Autowired
	private IUserOrderService iUserOrderService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUserOrder(@RequestBody UserOrderDto userOrderDto){
		iUserOrderService.createUserOrder(userOrderDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<UserOrder> fetchOrderRatingDetails(@RequestParam String id){
		
		UserOrder userOrder = iUserOrderService.fetchUserOrder(id);
	return ResponseEntity.status(HttpStatus.OK).body(userOrder);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUserOrderDetails(@RequestBody UserOrderDto userOrderDto){
		
		boolean isUpdated = iUserOrderService.updateUserOrder(userOrderDto);
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
	public ResponseEntity<ResponseDto> deleteUserOrderDetails(@RequestParam String id){
		
		boolean isDeleted = iUserOrderService.deleteUserOrder(id);
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
	public ApiResponse<List<UserOrder>> getAllUserOrders(){
	List<UserOrder> userOrder =	iUserOrderService.fetchAllUserOrders();
	
	return new ApiResponse<List<UserOrder>>("200", "success", userOrder);
	}




}
