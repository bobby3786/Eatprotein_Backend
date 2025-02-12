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

import com.orders.dto.OrderRatingDto;
import com.orders.entity.OrderRating;
import com.orders.service.IOrderRatingService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("orders/orderrating")
public class OrderRatingController {
	
	@Autowired
	private IOrderRatingService iOrderRatingService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createOrderRating(@RequestBody OrderRatingDto orderRatingDto){
		iOrderRatingService.createOrderRating(orderRatingDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<OrderRating> fetchOrderRatingDetails(@RequestParam String id){
		
		OrderRating orderRating = iOrderRatingService.fetchOrderRating(id);
	return ResponseEntity.status(HttpStatus.OK).body(orderRating);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateOrderRatingDetails(@RequestBody OrderRatingDto orderRatingDto){
		
		boolean isUpdated = iOrderRatingService.updateOrderRating(orderRatingDto);
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
	public ResponseEntity<ResponseDto> deleteOrderRatingDetails(@RequestParam String id){
		
		boolean isDeleted = iOrderRatingService.deleteOrderRating(id);
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
	public ApiResponse<List<OrderRating>> getAllOrderRatings(){
	List<OrderRating> orderRating =	iOrderRatingService.fetchAllOrderRatings();
	
	return new ApiResponse<List<OrderRating>>("200", "success", orderRating);
	}




}
