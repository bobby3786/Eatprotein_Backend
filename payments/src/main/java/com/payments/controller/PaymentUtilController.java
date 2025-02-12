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

import com.payments.dto.PaymentUtilDto;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;
import com.payments.entity.PaymentUtil;
import com.payments.service.IPaymentUtilService;

@RestController
@RequestMapping("payments/paymentutil")
public class PaymentUtilController {
	
	@Autowired
	private IPaymentUtilService iPaymentUtilService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createPaymentUtil(@RequestBody PaymentUtilDto paymentUtilDto){
		iPaymentUtilService.createPaymentUtil(paymentUtilDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<PaymentUtil> fetchPaymentUtilDetails(@RequestParam("id") int id){
		
		PaymentUtil paymentUtil = iPaymentUtilService.fetchPaymentUtil(id);
	return ResponseEntity.status(HttpStatus.OK).body(paymentUtil);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updatePaymentUtilDetails(@RequestBody PaymentUtilDto paymentUtilDto){
		
		boolean isUpdated = iPaymentUtilService.updatePaymentUtil(paymentUtilDto);
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
	public ResponseEntity<ResponseDto> deletePaymentUtilDetails(@RequestParam("id") int id){
		
		boolean isDeleted = iPaymentUtilService.deletePaymentUtil(id);
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
	public ApiResponse<List<PaymentUtil>> getAllPaymentUtils(){
	List<PaymentUtil> paymentUtil =	iPaymentUtilService.fetchAllPaymentUtils();
	
	return new ApiResponse<List<PaymentUtil>>("200", "success", paymentUtil);
	}




}
