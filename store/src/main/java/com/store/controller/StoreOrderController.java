package com.store.controller;

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
import com.store.dto.StoreOrderDto;
import com.store.entity.StoreOrder;
import com.store.service.IStoreOrderService;


@RestController
@RequestMapping("store/storeorder")
public class StoreOrderController {
	
	@Autowired
	private IStoreOrderService iStoreOrderService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createStoreOrder(@RequestBody StoreOrderDto storeOrderDto){
		iStoreOrderService.createStoreOrder(storeOrderDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<StoreOrder> fetchStoreOrderDetails(@RequestParam("id") String id){
		
		StoreOrder storeOrder = iStoreOrderService.fetchStoreOrder(id);
	return ResponseEntity.status(HttpStatus.OK).body(storeOrder);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateStoreOrderDetails(@RequestBody StoreOrderDto storeOrderDto){
		
		boolean isUpdated = iStoreOrderService.updateStoreOrder(storeOrderDto);
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
	public ResponseEntity<ResponseDto> deleteStoreOrderDetails(@RequestParam("id") String id){
		
		boolean isDeleted = iStoreOrderService.deleteStoreOrder(id);
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
	public ApiResponse<List<StoreOrder>> getAllStoreOrders(){
	List<StoreOrder> storeOrder =	iStoreOrderService.fetchAllStoreOrders();
	
	return new ApiResponse<List<StoreOrder>>("200", "success", storeOrder);
	}



}
