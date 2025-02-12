package com.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.dto.UserOrderDto;
import com.orders.entity.UserOrder;
import com.orders.repository.UserOrderRepository;
import com.orders.service.IUserOrderService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserOrderServiceImpl implements IUserOrderService{
	
	@Autowired
	private UserOrderRepository userOrderRepository;

	@Override
	public void createUserOrder(UserOrderDto userOrderDto) {

		UserOrder userOrder=new UserOrder();
		ObjectEntityCheckutil.copyNonNullProperties(userOrderDto, userOrder);
		userOrderRepository.save(userOrder);
		
	}

	@Override
	public UserOrder fetchUserOrder(String id) {
	
		UserOrder userOrder = userOrderRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("userOrder", "id", id)
				);
		
		return userOrder;
		
	}

	@Override
	public boolean updateUserOrder(UserOrderDto userOrderDto) {
	
		UserOrder userOrder = userOrderRepository.findById(userOrderDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("userOrder", "id", userOrderDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userOrderDto, userOrder);
		userOrderRepository.save(userOrder);
		
		return true;
	}

	@Override
	public boolean deleteUserOrder(String id) {

		userOrderRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("UserOrder", "id", id)
				);
		userOrderRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserOrder> fetchAllUserOrders() {
		
		return userOrderRepository.findAll();
	}




}
