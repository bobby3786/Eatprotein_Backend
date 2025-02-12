package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.StoreRatingDto;
import com.store.entity.StoreRating;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.repository.StoreRatingRepository;
import com.store.service.IStoreRatingService;

@Service
public class StoreRatingServiceImpl implements IStoreRatingService {
	
	@Autowired
	private StoreRatingRepository storeRatingRepository;

	@Override
	public void createStoreRating(StoreRatingDto storeRatingDto) {

		StoreRating storeRating=new StoreRating();
		ObjectEntityCheckutil.copyNonNullProperties(storeRatingDto, storeRating);
		storeRatingRepository.save(storeRating);
		
	}

	@Override
	public StoreRating fetchStoreRating(String id) {
	
		StoreRating storeRating = storeRatingRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreRating", "id", id)
				);
		
		return storeRating;
		
	}

	@Override
	public boolean updateStoreRating(StoreRatingDto storeRatingDto) {
	
		StoreRating storeRating = storeRatingRepository.findById(storeRatingDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", storeRatingDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(storeRatingDto, storeRating);
		storeRatingRepository.save(storeRating);
		
		return true;
	}

	@Override
	public boolean deleteStoreRating(String id) {

		storeRatingRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("StoreRating", "id", id)
				);
		storeRatingRepository.deleteById(id);
		return true;
	}

	@Override
	public List<StoreRating> fetchAllStoreRatings() {
		
		return storeRatingRepository.findAll();
	}




}
