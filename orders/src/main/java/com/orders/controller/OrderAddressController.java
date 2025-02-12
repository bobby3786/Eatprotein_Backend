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

import com.orders.dto.OrderAddressDto;
import com.orders.entity.OrderAddress;
import com.orders.service.IOrderAddressService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;


@RestController
@RequestMapping("orders/orderaddress")
public class OrderAddressController {
	
	@Autowired
	private IOrderAddressService iOrderAddressService;//main interface
	
//	IorderAddressServiceimple
	//
	
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createOrderAddress(@RequestBody OrderAddressDto orderAddressDto){
		iOrderAddressService.createOrderAddress(orderAddressDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<OrderAddress> fetchOrderAddressDetails(@RequestParam String id){
		
		OrderAddress orderAddress = iOrderAddressService.fetchOrderAddress(id);
	return ResponseEntity.status(HttpStatus.OK).body(orderAddress);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateOrderAddressDetails(@RequestBody OrderAddressDto orderAddressDto){
		
		boolean isUpdated = iOrderAddressService.updateOrderAddress(orderAddressDto);
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
	public ResponseEntity<ResponseDto> deleteOrderAddressDetails(@RequestParam String id){
		
		boolean isDeleted = iOrderAddressService.deleteOrderAddress(id);
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
	public ApiResponse<List<OrderAddress>> getAllOrderAddresses(){
	List<OrderAddress> orderAddress =	iOrderAddressService.fetchAllOrderAddresses();
	
	return new ApiResponse<List<OrderAddress>>("200", "success", orderAddress);
	}



}
