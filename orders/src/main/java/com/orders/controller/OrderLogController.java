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

import com.orders.dto.OrderLogDto;
import com.orders.entity.OrderLog;
import com.orders.service.IOrderLogService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("orders/orderlog")
public class OrderLogController {

	@Autowired
	private IOrderLogService iOrderLogService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createOrderLog(@RequestBody OrderLogDto orderLogDto){
		iOrderLogService.createOrderLog(orderLogDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<OrderLog> fetchOrderLogDetails(@RequestParam String id){
		
		OrderLog orderLog = iOrderLogService.fetchOrderLog(id);
	return ResponseEntity.status(HttpStatus.OK).body(orderLog);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateOrderLogDetails(@RequestBody OrderLogDto orderLogDto){
		
		boolean isUpdated = iOrderLogService.updateOrderLog(orderLogDto);
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
	public ResponseEntity<ResponseDto> deleteOrderLogDetails(@RequestParam String id){
		
		boolean isDeleted = iOrderLogService.deleteOrderLog(id);
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
	public ApiResponse<List<OrderLog>> getAllorderLogs(){
	List<OrderLog> orderLog =	iOrderLogService.fetchAllOrderLogs();
	
	return new ApiResponse<List<OrderLog>>("200", "success", orderLog);
	}


	
}
