package com.catalog.service;

import java.util.List;

import com.catalog.dto.ProductRatingDto;
import com.catalog.entity.ProductRating;

public interface IProductRatingService {
	
	void createProductRating(ProductRatingDto productRatingDto);
	ProductRating fetchProductRating(int id);
	boolean updateProductRating(ProductRatingDto productRatingDto);
	boolean deleteProductRating(int id);
	List<ProductRating> fetchAllProductRatings();

}
