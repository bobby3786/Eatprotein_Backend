package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.store.dto.StoreDto;
import com.store.entity.Store;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.repository.StoreRepository;
import com.store.service.IStoreService;

@Service
public class StoreServiceImpl implements IStoreService{
	
	@Autowired
	private StoreRepository storeRepository;

	@Override
	public void createStore(StoreDto storeDto) {

		Store store=new Store();
		ObjectEntityCheckutil.copyNonNullProperties(storeDto, store);
		storeRepository.save(store);
		
	}

	@Override
	public Store fetchStore(String id) {
	
		Store store = storeRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("Store", "id", id)
				);
		
		return store;
		
	}

	@Override
	public boolean updateStore(StoreDto storeDto) {
	
		Store store = storeRepository.findById(storeDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("Store", "id", storeDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(storeDto, store);
		storeRepository.save(store);
		
		return true;
	}

	@Override
	public boolean deleteStore(String id) {

		storeRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("Store", "id", id)
				);
		storeRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Store> fetchAllStores() {
		
		return storeRepository.findAll();
	}

	@Override
	public List<Store> getStoresWithField(String field) {
		
		return storeRepository.findAll(Sort.by(Sort.Direction.ASC,field));
	}

	@Override
	public Page<Store> getStoresWithPaginationAndSortingFields(int offset, int pageSize) {
	Page<Store> stores =  storeRepository.findAll(PageRequest.of(offset, pageSize));
		return stores;
	}

	@Override
	public Page<Store> getStoresWithPaginationAndSortingField(int offset, int pageSize, String field) {
		Page<Store> stores =  storeRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return stores;
	}



}
