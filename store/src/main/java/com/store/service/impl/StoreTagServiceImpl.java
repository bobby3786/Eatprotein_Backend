package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.StoreTagDto;
import com.store.entity.StoreTag;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.repository.StoreTagRepository;
import com.store.service.IStoreTagService;

@Service
public class StoreTagServiceImpl implements IStoreTagService{

	@Autowired
	private StoreTagRepository storeTagRepository;

	@Override
	public void createStoreTag(StoreTagDto storeTagDto) {

		StoreTag storeTag=new StoreTag();
		ObjectEntityCheckutil.copyNonNullProperties(storeTagDto, storeTag);
		storeTagRepository.save(storeTag);
		
	}

	@Override
	public StoreTag fetchStoreTag(String id) {
	
		StoreTag storeTag = storeTagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreTag", "id", id)
				);
		
		return storeTag;
		
	}

	@Override
	public boolean updateStoreTag(StoreTagDto storeTagDto) {
	
		StoreTag storeTag = storeTagRepository.findById(storeTagDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("StoreTag", "id", storeTagDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(storeTagDto, storeTag);
		storeTagRepository.save(storeTag);
		
		return true;
	}

	@Override
	public boolean deleteStoreTag(String id) {

		storeTagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreTag", "id", id)
				);
		storeTagRepository.deleteById(id);
		return true;
	}

	@Override
	public List<StoreTag> fetchAllStoreTags() {
		
		return storeTagRepository.findAll();
	}


	
}
