package com.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.dto.OrderLogDto;
import com.orders.entity.OrderLog;
import com.orders.repository.OrderLogRepository;
import com.orders.service.IOrderLogService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class OrderLogServiceImpl implements IOrderLogService{
	
	@Autowired
	private OrderLogRepository orderLogRepository;

	@Override
	public void createOrderLog(OrderLogDto orderLogDto) {

		OrderLog orderLog=new OrderLog();
		ObjectEntityCheckutil.copyNonNullProperties(orderLogDto, orderLog);
		orderLogRepository.save(orderLog);
		
	}

	@Override
	public OrderLog fetchOrderLog(String id) {
	
		OrderLog orderLog = orderLogRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("orderLog", "id", id)
				);
		
		return orderLog;
		
	}

	@Override
	public boolean updateOrderLog(OrderLogDto orderLogDto) {
	
		OrderLog orderLog = orderLogRepository.findById(orderLogDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", orderLogDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(orderLogDto, orderLog);
		orderLogRepository.save(orderLog);
		
		return true;
	}

	@Override
	public boolean deleteOrderLog(String id) {

		orderLogRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("orderLog", "id", id)
				);
		orderLogRepository.deleteById(id);
		return true;
	}

	@Override
	public List<OrderLog> fetchAllOrderLogs() {
		
		return orderLogRepository.findAll();
	}



}
