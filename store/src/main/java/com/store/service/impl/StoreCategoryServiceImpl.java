package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.StoreCategoryDto;
import com.store.entity.StoreCategory;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.repository.StoreCategoryRepository;
import com.store.service.IStoreCategoryService;

@Service
public class StoreCategoryServiceImpl implements IStoreCategoryService{
	
	@Autowired
	private StoreCategoryRepository storeCategoryRepository;

	@Override
	public void createStoreCategory(StoreCategoryDto storeCategoryDto) {

		StoreCategory storeCategory=new StoreCategory();
		ObjectEntityCheckutil.copyNonNullProperties(storeCategoryDto, storeCategory);
		storeCategoryRepository.save(storeCategory);
		
	}

	@Override
	public StoreCategory fetchStoreCategory(String id) {
	
		StoreCategory storeCategory = storeCategoryRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreCategory", "id", id)
				);
		
		return storeCategory;
		
	}

	@Override
	public boolean updateStoreCategory(StoreCategoryDto storeCategoryDto) {
	
		StoreCategory storeCategory = storeCategoryRepository.findById(storeCategoryDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", storeCategoryDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(storeCategoryDto, storeCategory);
		storeCategoryRepository.save(storeCategory);
		
		return true;
	}

	@Override
	public boolean deleteStoreCategory(String id) {

		storeCategoryRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreCategory", "id", id)
				);
		storeCategoryRepository.deleteById(id);
		return true;
	}

	@Override
	public List<StoreCategory> fetchAllStoreCategories() {
		
		return storeCategoryRepository.findAll();
	}



}
