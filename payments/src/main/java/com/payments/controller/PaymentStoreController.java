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

import com.payments.dto.PaymentStoreDto;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;
import com.payments.entity.PaymentStore;
import com.payments.service.IPaymentStoreService;

@RestController
@RequestMapping("payments/paymentstore")
public class PaymentStoreController {
	
	@Autowired
	private IPaymentStoreService iPaymentStoreService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createPaymentStore(@RequestBody PaymentStoreDto paymentStoreDto){
		iPaymentStoreService.createPaymentStore(paymentStoreDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<PaymentStore> fetchPaymentStoreDetails(@RequestParam("id") int id){
		
		PaymentStore paymentStore = iPaymentStoreService.fetchPaymentStore(id);
	return ResponseEntity.status(HttpStatus.OK).body(paymentStore);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updatePaymentStoreDetails(@RequestBody PaymentStoreDto paymentStoreDto){
		
		boolean isUpdated = iPaymentStoreService.updatePaymentStore(paymentStoreDto);
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
	public ResponseEntity<ResponseDto> deletePaymentStoreDetails(@RequestParam("id") int id){
		
		boolean isDeleted = iPaymentStoreService.deletePaymentStore(id);
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
	public ApiResponse<List<PaymentStore>> getAllPaymentStores(){
	List<PaymentStore> paymentStore =	iPaymentStoreService.fetchAllPaymentStores();
	
	return new ApiResponse<List<PaymentStore>>("200", "success", paymentStore);
	}




}
