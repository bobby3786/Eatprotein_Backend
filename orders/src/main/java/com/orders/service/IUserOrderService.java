package com.orders.service;

import java.util.List;

import com.orders.dto.UserOrderDto;
import com.orders.entity.UserOrder;

public interface IUserOrderService {
	
	void createUserOrder(UserOrderDto userOrderDto);
	UserOrder fetchUserOrder(String id);
	boolean updateUserOrder(UserOrderDto userOrderDto);
	boolean deleteUserOrder(String id);
	List<UserOrder> fetchAllUserOrders();

}
