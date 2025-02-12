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

import com.orders.dto.OrderPaymentDto;
import com.orders.entity.OrderPayment;
import com.orders.service.IOrderPaymentService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("orders/orderpayment")
public class OrderPaymentController {
	
	@Autowired
	private IOrderPaymentService iOrderPaymentService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createOrderPayment(@RequestBody OrderPaymentDto orderPaymentDto){
		iOrderPaymentService.createOrderPayment(orderPaymentDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<OrderPayment> fetchOrderPaymentDetails(@RequestParam String id){
		
		OrderPayment orderPayment = iOrderPaymentService.fetchOrderPayment(id);
	return ResponseEntity.status(HttpStatus.OK).body(orderPayment);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateOrderPaymentDetails(@RequestBody OrderPaymentDto orderPaymentDto){
		
		boolean isUpdated = iOrderPaymentService.updateOrderPayment(orderPaymentDto);
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
	public ResponseEntity<ResponseDto> deleteOrderPaymentDetails(@RequestParam String id){
		
		boolean isDeleted = iOrderPaymentService.deleteOrderPayment(id);
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
	public ApiResponse<List<OrderPayment>> getAllOrderPayments(){
	List<OrderPayment> orderPayment =	iOrderPaymentService.fetchAllOrderPayments();
	
	return new ApiResponse<List<OrderPayment>>("200", "success", orderPayment);
	}




}
