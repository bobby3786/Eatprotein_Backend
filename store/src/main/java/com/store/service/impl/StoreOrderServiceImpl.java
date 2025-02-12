package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.StoreOrderDto;
import com.store.entity.StoreOrder;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.repository.StoreOrderRepository;
import com.store.service.IStoreOrderService;

@Service
public class StoreOrderServiceImpl implements IStoreOrderService{
	
	
	@Autowired
	private StoreOrderRepository storeOrderRepository;

	@Override
	public void createStoreOrder(StoreOrderDto storeOrderDto) {

		StoreOrder storeOrder=new StoreOrder();
		ObjectEntityCheckutil.copyNonNullProperties(storeOrderDto, storeOrder);
		storeOrderRepository.save(storeOrder);
		
	}

	@Override
	public StoreOrder fetchStoreOrder(String id) {
	
		StoreOrder storeOrder = storeOrderRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreOrder", "id", id)
				);
		
		return storeOrder;
		
	}

	@Override
	public boolean updateStoreOrder(StoreOrderDto storeOrderDto) {
	
		StoreOrder storeOrder = storeOrderRepository.findById(storeOrderDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", storeOrderDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(storeOrderDto, storeOrder);
		storeOrderRepository.save(storeOrder);
		
		return true;
	}

	@Override
	public boolean deleteStoreOrder(String id) {

		storeOrderRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreOrder", "id", id)
				);
		storeOrderRepository.deleteById(id);
		return true;
	}

	@Override
	public List<StoreOrder> fetchAllStoreOrders() {
		
		return storeOrderRepository.findAll();
	}



}
