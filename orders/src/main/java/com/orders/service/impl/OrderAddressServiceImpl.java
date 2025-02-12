package com.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.dto.OrderAddressDto;
import com.orders.entity.OrderAddress;
import com.orders.repository.OrderAddressRepository;
import com.orders.service.IOrderAddressService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class OrderAddressServiceImpl implements IOrderAddressService{
	

	@Autowired
	private OrderAddressRepository orderAddressRepository;

	@Override
	public void createOrderAddress(OrderAddressDto orderAddressDto) {

		OrderAddress orderAddress=new OrderAddress();
		ObjectEntityCheckutil.copyNonNullProperties(orderAddressDto, orderAddress);
		orderAddressRepository.save(orderAddress);
		
	}

	@Override
	public OrderAddress fetchOrderAddress(String id) {
	
		OrderAddress orderAddress = orderAddressRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("OrderAddress", "id", id)
				);
		
		return orderAddress;
		
	}

	@Override
	public boolean updateOrderAddress(OrderAddressDto orderAddressDto) {
	
		
		OrderAddress orderAddress = orderAddressRepository.findById(orderAddressDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderAddress", "id", orderAddressDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(orderAddressDto, orderAddress);
		orderAddressRepository.save(orderAddress);
		
		return true;
	}

	@Override
	public boolean deleteOrderAddress(String id) {

		orderAddressRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("OrderAddress", "id", id)
				);
		orderAddressRepository.deleteById(id);
		return true;
	}

	@Override
	public List<OrderAddress> fetchAllOrderAddresses() {
		
		return orderAddressRepository.findAll();
	}



}
