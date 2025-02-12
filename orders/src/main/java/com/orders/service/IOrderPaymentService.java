package com.orders.service;

import java.util.List;

import com.orders.dto.OrderPaymentDto;
import com.orders.entity.OrderPayment;

public interface IOrderPaymentService {
	
	void createOrderPayment(OrderPaymentDto orderPaymentDto);
	OrderPayment fetchOrderPayment(String id);
	boolean updateOrderPayment(OrderPaymentDto orderPaymentDto);
	boolean deleteOrderPayment(String id);
	List<OrderPayment> fetchAllOrderPayments();

}
