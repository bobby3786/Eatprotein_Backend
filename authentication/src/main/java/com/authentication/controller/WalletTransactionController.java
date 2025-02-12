package com.authentication.controller;

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

import com.authentication.dto.WalletTransactionDto;
import com.authentication.entity.WalletTransaction;
import com.authentication.service.IWalletTransactionService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/wallettransaction")
public class WalletTransactionController {
	

	@Autowired
	private IWalletTransactionService iWalletTransactionService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createWalletTransaction(@RequestBody WalletTransactionDto walletTransactionDto){
		iWalletTransactionService.createWalletTransaction(walletTransactionDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<WalletTransaction> fetchWalletTransactionDetails(@RequestParam int id){
		
		WalletTransaction walletTransaction = iWalletTransactionService.fetchWalletTransaction(id);
	return ResponseEntity.status(HttpStatus.OK).body(walletTransaction);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateWalletTransactionDetails(@RequestBody WalletTransactionDto walletTransactionDto){
		
		boolean isUpdated = iWalletTransactionService.updateWalletTransaction(walletTransactionDto);
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
	public ResponseEntity<ResponseDto> deleteWalletTransactionDetails(@RequestParam int id){
		
		boolean isDeleted = iWalletTransactionService.deleteWalletTransaction(id);
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
	public ApiResponse<List<WalletTransaction>> getAllWalletTransactions(){
	List<WalletTransaction> walletTransaction =	iWalletTransactionService.fetchAllWalletTransactions();
	
	return new ApiResponse<List<WalletTransaction>>("200", "success", walletTransaction);
	}



}
