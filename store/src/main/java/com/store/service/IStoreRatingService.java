package com.store.service;

import java.util.List;

import com.store.dto.StoreRatingDto;
import com.store.entity.StoreRating;

public interface IStoreRatingService {
	
	void createStoreRating(StoreRatingDto storeRatingDto);
	StoreRating fetchStoreRating(String id);
	boolean updateStoreRating(StoreRatingDto storeRatingDto);
	boolean deleteStoreRating(String id);
	List<StoreRating> fetchAllStoreRatings();

}
