package com.store.service;

import java.util.List;

import com.store.dto.StoreTagDto;
import com.store.entity.StoreTag;

public interface IStoreTagService {
	
	void createStoreTag(StoreTagDto storeTagDto);
	StoreTag fetchStoreTag(String id);
	boolean updateStoreTag(StoreTagDto storeTagDto);
	boolean deleteStoreTag(String id);
	List<StoreTag> fetchAllStoreTags();

}
