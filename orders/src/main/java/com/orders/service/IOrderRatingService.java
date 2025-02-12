package com.orders.service;

import java.util.List;

import com.orders.dto.OrderRatingDto;
import com.orders.entity.OrderRating;

public interface IOrderRatingService {
	
	
	void createOrderRating(OrderRatingDto orderRatingDto);
	OrderRating fetchOrderRating(String id);
	boolean updateOrderRating(OrderRatingDto orderRatingDto);
	boolean deleteOrderRating(String id);
	List<OrderRating> fetchAllOrderRatings();

}
