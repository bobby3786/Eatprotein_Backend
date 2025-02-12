package com.store.service;

import java.util.List;

import com.store.dto.StoreProductDto;
import com.store.entity.StoreProduct;

public interface IStoreProductService {
	
	void createStoreProduct(StoreProductDto storeProductDto);
	StoreProduct fetchStoreProduct(String id);
	boolean updateStoreProduct(StoreProductDto storeProductDto);
	boolean deleteStoreProduct(String id);
	List<StoreProduct> fetchAllStoreProducts();

}
