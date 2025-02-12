package com.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.dto.OrderRatingDto;
import com.orders.entity.OrderRating;
import com.orders.repository.OrderRatingRepository;
import com.orders.service.IOrderRatingService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class OrderRatingServiceImpl implements IOrderRatingService{
	
	@Autowired
	private OrderRatingRepository orderRatingRepository;

	@Override
	public void createOrderRating(OrderRatingDto orderRatingDto) {

		OrderRating orderRating=new OrderRating();
		ObjectEntityCheckutil.copyNonNullProperties(orderRatingDto, orderRating);
		orderRatingRepository.save(orderRating);
		
	}

	@Override
	public OrderRating fetchOrderRating(String id) {
	
		OrderRating orderRating = orderRatingRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("OrderRating", "id", id)
				);
		
		return orderRating;
		
	}

	@Override
	public boolean updateOrderRating(OrderRatingDto orderRatingDto) {
	
		OrderRating orderRating = orderRatingRepository.findById(orderRatingDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", orderRatingDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(orderRatingDto, orderRating);
		orderRatingRepository.save(orderRating);
		
		return true;
	}

	@Override
	public boolean deleteOrderRating(String id) {

		orderRatingRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("OrderRating", "id", id)
				);
		orderRatingRepository.deleteById(id);
		return true;
	}

	@Override
	public List<OrderRating> fetchAllOrderRatings() {
		
		return orderRatingRepository.findAll();
	}



}
