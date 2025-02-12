package com.payments.controller;

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

import com.payments.dto.PaymentDto;
import com.payments.entity.Payment;
import com.payments.service.IPaymentService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;


@RestController
@RequestMapping("payments/payment")
public class PaymentController {
	
	@Autowired
	private IPaymentService iPaymentService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createPayment(@RequestBody PaymentDto paymentDto){
		iPaymentService.createPayment(paymentDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Payment> fetchPaymentDetails(@RequestParam("id") int id){
		
		Payment payment = iPaymentService.fetchPayment(id);
	return ResponseEntity.status(HttpStatus.OK).body(payment);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updatePaymentDetails(@RequestBody PaymentDto paymentDto){
		
		boolean isUpdated = iPaymentService.updatePayment(paymentDto);
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
	public ResponseEntity<ResponseDto> deletePaymentDetails(@RequestParam("id") int id){
		
		boolean isDeleted = iPaymentService.deletePayment(id);
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
	public ApiResponse<List<Payment>> getAllOrderRatings(){
	List<Payment> payment =	iPaymentService.fetchAllPayments();
	
	return new ApiResponse<List<Payment>>("200", "success", payment);
	}



}
