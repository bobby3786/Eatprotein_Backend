package com.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.dto.OrderItemDto;
import com.orders.entity.OrderItem;
import com.orders.repository.OrderItemRepository;
import com.orders.service.IOrderItemService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class OrderItemServiceImpl implements IOrderItemService{
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void createOrderItem(OrderItemDto orderItemDto) {

		OrderItem orderItem=new OrderItem();
		ObjectEntityCheckutil.copyNonNullProperties(orderItemDto, orderItem);
		orderItemRepository.save(orderItem);
		
	}

	@Override
	public OrderItem fetchOrderItem(String id) {
	
		OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", id)
				);
		
		return orderItem;
		
	}

	@Override
	public boolean updateOrderItem(OrderItemDto orderItemDto) {
	
		OrderItem orderItem = orderItemRepository.findById(orderItemDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", orderItemDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(orderItemDto, orderItem);
		orderItemRepository.save(orderItem);
		
		return true;
	}

	@Override
	public boolean deleteOrderItem(String id) {

		orderItemRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", id)
				);
		orderItemRepository.deleteById(id);
		return true;
	}

	@Override
	public List<OrderItem> fetchAllOrderItems() {
		
		return orderItemRepository.findAll();
	}



}
