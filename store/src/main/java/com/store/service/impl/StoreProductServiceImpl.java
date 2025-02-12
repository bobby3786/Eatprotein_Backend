package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.StoreProductDto;
import com.store.entity.StoreProduct;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.repository.StoreProductRepository;
import com.store.service.IStoreProductService;

@Service
public class StoreProductServiceImpl implements IStoreProductService {
	
	@Autowired
	private StoreProductRepository storeProductRepository;

	@Override
	public void createStoreProduct(StoreProductDto storeProductDto) {

		StoreProduct storeProduct=new StoreProduct();
		ObjectEntityCheckutil.copyNonNullProperties(storeProductDto, storeProduct);
		storeProductRepository.save(storeProduct);
		
	}

	@Override
	public StoreProduct fetchStoreProduct(String id) {
	
		StoreProduct storeProduct = storeProductRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreProduct", "id", id)
				);
		
		return storeProduct;
		
	}

	@Override
	public boolean updateStoreProduct(StoreProductDto storeProductDto) {
	
		StoreProduct storeProduct = storeProductRepository.findById(storeProductDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("storeProduct", "id", storeProductDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(storeProductDto, storeProduct);
		storeProductRepository.save(storeProduct);
		
		return true;
	}

	@Override
	public boolean deleteStoreProduct(String id) {

		storeProductRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreProduct", "id", id)
				);
		storeProductRepository.deleteById(id);
		return true;
	}

	@Override
	public List<StoreProduct> fetchAllStoreProducts() {
		
		return storeProductRepository.findAll();
	}



}
