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

import com.orders.dto.OrderItemDto;
import com.orders.entity.OrderItem;
import com.orders.service.IOrderItemService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("orders/orderitem")
public class OrderItemController {

	@Autowired
	private IOrderItemService iOrderItemService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createOrderItem(@RequestBody OrderItemDto orderItemDto){
		iOrderItemService.createOrderItem(orderItemDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<OrderItem> fetchOrderItemDetails(@RequestParam String id){
		
		OrderItem orderItem = iOrderItemService.fetchOrderItem(id);
	return ResponseEntity.status(HttpStatus.OK).body(orderItem);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateOrderItemDetails(@RequestBody OrderItemDto orderItemDto){
		
		boolean isUpdated = iOrderItemService.updateOrderItem(orderItemDto);
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
	public ResponseEntity<ResponseDto> deleteOrderItemDetails(@RequestParam String id){
		
		boolean isDeleted = iOrderItemService.deleteOrderItem(id);
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
	public ApiResponse<List<OrderItem>> getAllorderItems(){
	List<OrderItem> orderItem =	iOrderItemService.fetchAllOrderItems();
	
	return new ApiResponse<List<OrderItem>>("200", "success", orderItem);
	}


	
}
