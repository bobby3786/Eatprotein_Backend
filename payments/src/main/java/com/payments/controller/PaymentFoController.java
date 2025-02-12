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

import com.payments.dto.PaymentFoDto;
import com.payments.entity.PaymentFo;
import com.payments.service.IPaymentFoService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("payments/paymentfo")
public class PaymentFoController {
	
	@Autowired
	private IPaymentFoService iPaymentFoService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createPaymentFo(@RequestBody PaymentFoDto paymentFoDto){
		iPaymentFoService.createPaymentFo(paymentFoDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<PaymentFo> fetchPaymentFoDetails(@RequestParam("id") int id){
		
		PaymentFo paymentFo = iPaymentFoService.fetchPaymentFo(id);
	return ResponseEntity.status(HttpStatus.OK).body(paymentFo);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updatePaymentFoDetails(@RequestBody PaymentFoDto paymentFoDto){
		
		boolean isUpdated = iPaymentFoService.updatePaymentFo(paymentFoDto);
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
	public ResponseEntity<ResponseDto> deletePaymentFoDetails(@RequestParam("id") int id){
		
		boolean isDeleted = iPaymentFoService.deletePaymentFo(id);
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
	public ApiResponse<List<PaymentFo>> getAllPaymentFos(){
	List<PaymentFo> paymentFo =	iPaymentFoService.fetchAllPaymentFos();
	
	return new ApiResponse<List<PaymentFo>>("200", "success", paymentFo);
	}



}
