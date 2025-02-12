package com.store.service;

import java.util.List;

import com.store.dto.StoreOrderDto;
import com.store.entity.StoreOrder;

public interface IStoreOrderService {
	
	void createStoreOrder(StoreOrderDto storeOrderDto);
	StoreOrder fetchStoreOrder(String id);
	boolean updateStoreOrder(StoreOrderDto storeOrderDto);
	boolean deleteStoreOrder(String id);
	List<StoreOrder> fetchAllStoreOrders();

}
