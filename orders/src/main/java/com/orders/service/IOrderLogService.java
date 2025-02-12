package com.orders.service;

import java.util.List;

import com.orders.dto.OrderLogDto;
import com.orders.entity.OrderLog;

public interface IOrderLogService {
	
	void createOrderLog(OrderLogDto orderLogDto);
	OrderLog fetchOrderLog(String id);
	boolean updateOrderLog(OrderLogDto orderLogDto);
	boolean deleteOrderLog(String id);
	List<OrderLog> fetchAllOrderLogs();

}
