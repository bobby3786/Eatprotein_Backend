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

import com.payments.dto.PaymentDbDto;
import com.payments.entity.PaymentDb;
import com.payments.service.IPaymentDbService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("payments/paymentdb")
public class PaymentDbController {
	
	@Autowired
	private IPaymentDbService iPaymentDbService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createPaymentDb(@RequestBody PaymentDbDto paymentDbDto){
		iPaymentDbService.createPaymentDb(paymentDbDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<PaymentDb> fetchPaymentDbDetails(@RequestParam("id") int id){
		
		PaymentDb paymentDb = iPaymentDbService.fetchPaymentDb(id);
	return ResponseEntity.status(HttpStatus.OK).body(paymentDb);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updatePaymentDbDetails(@RequestBody PaymentDbDto paymentDbDto){
		
		boolean isUpdated = iPaymentDbService.updatePaymentDb(paymentDbDto);
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
	public ResponseEntity<ResponseDto> deletePaymentDbDetails(@RequestParam("id") int id){
		
		boolean isDeleted = iPaymentDbService.deletePaymentDb(id);
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
	public ApiResponse<List<PaymentDb>> getAllPaymentDbs(){
	List<PaymentDb> paymentDb =	iPaymentDbService.fetchAllPaymentDbs();
	
	return new ApiResponse<List<PaymentDb>>("200", "success", paymentDb);
	}




}
