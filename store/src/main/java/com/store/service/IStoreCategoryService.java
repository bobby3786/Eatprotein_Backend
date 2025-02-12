package com.store.service;

import java.util.List;

import com.store.dto.StoreCategoryDto;
import com.store.entity.StoreCategory;

public interface IStoreCategoryService {
	
	void createStoreCategory(StoreCategoryDto storeCategoryDto);
	StoreCategory fetchStoreCategory(String id);
	boolean updateStoreCategory(StoreCategoryDto storeCategoryDto);
	boolean deleteStoreCategory(String id);
	List<StoreCategory> fetchAllStoreCategories();

}
