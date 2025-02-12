package com.orders.service;

import java.util.List;

import com.orders.dto.OrderAddressDto;
import com.orders.entity.OrderAddress;


public interface IOrderAddressService {
	
	void createOrderAddress(OrderAddressDto OrderAddressDto);
	OrderAddress fetchOrderAddress(String id);
	boolean updateOrderAddress(OrderAddressDto OrderAddressDto);
	boolean deleteOrderAddress(String id);
	List<OrderAddress> fetchAllOrderAddresses();

}
