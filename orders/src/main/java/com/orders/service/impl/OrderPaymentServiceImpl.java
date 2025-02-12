package com.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.dto.OrderPaymentDto;
import com.orders.entity.OrderPayment;
import com.orders.repository.OrderPaymentRepository;
import com.orders.service.IOrderPaymentService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class OrderPaymentServiceImpl implements IOrderPaymentService{
	
	@Autowired
	private OrderPaymentRepository orderPaymentRepository;

	@Override
	public void createOrderPayment(OrderPaymentDto orderPaymentDto) {

		OrderPayment orderPayment=new OrderPayment();
		ObjectEntityCheckutil.copyNonNullProperties(orderPaymentDto, orderPayment);
		orderPaymentRepository.save(orderPayment);
		
	}

	@Override
	public OrderPayment fetchOrderPayment(String id) {
	
		OrderPayment orderPayment = orderPaymentRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("orderPayment", "id", id)
				);
		
		return orderPayment;
		
	}

	@Override
	public boolean updateOrderPayment(OrderPaymentDto orderPaymentDto) {
	
		OrderPayment orderPayment = orderPaymentRepository.findById(orderPaymentDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", orderPaymentDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(orderPaymentDto, orderPayment);
		orderPaymentRepository.save(orderPayment);
		
		return true;
	}

	@Override
	public boolean deleteOrderPayment(String id) {

		orderPaymentRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("OrderPayment", "id", id)
				);
		orderPaymentRepository.deleteById(id);
		return true;
	}

	@Override
	public List<OrderPayment> fetchAllOrderPayments() {
		
		return orderPaymentRepository.findAll();
	}



}
