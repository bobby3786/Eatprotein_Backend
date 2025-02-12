package com.orders.service;

import java.util.List;

import com.orders.dto.OrderItemDto;
import com.orders.entity.OrderItem;

public interface IOrderItemService {
	
	void createOrderItem(OrderItemDto orderItemDto);
	OrderItem fetchOrderItem(String id);
	boolean updateOrderItem(OrderItemDto orderItemDto);
	boolean deleteOrderItem(String id);
	List<OrderItem> fetchAllOrderItems();

}
