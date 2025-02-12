package com.store.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.store.dto.StoreDto;
import com.store.entity.Store;

public interface IStoreService {
	
	void createStore(StoreDto storeDto);
	Store fetchStore(String id);
	boolean updateStore(StoreDto storeDto);
	boolean deleteStore(String id);
	List<Store> fetchAllStores();
	List<Store> getStoresWithField(String field);
	Page<Store> getStoresWithPaginationAndSortingFields(int offset, int pazeSize);
	Page<Store> getStoresWithPaginationAndSortingField(int offset, int pageSize, String field);


}
