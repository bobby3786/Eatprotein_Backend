package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.ProductRatingDto;
import com.catalog.entity.ProductRating;
import com.catalog.repository.ProductRatingRepository;
import com.catalog.service.IProductRatingService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class ProductRatingServiceImpl implements IProductRatingService{
	
	@Autowired
	private ProductRatingRepository productRatingRepository;

	@Override
	public void createProductRating(ProductRatingDto productRatingDto) {

		ProductRating productRating=new ProductRating();
		ObjectEntityCheckutil.copyNonNullProperties(productRatingDto, productRating);
		productRatingRepository.save(productRating);
		
	}

	@Override
	public ProductRating fetchProductRating(int id) {
	
		ProductRating productRating = productRatingRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("productRating", "id", id)
				);
		
		return productRating;
		
	}

	@Override
	public boolean updateProductRating(ProductRatingDto productRatingDto) {
	
		ProductRating productRating = productRatingRepository.findById(productRatingDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("productRating", "id", productRatingDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(productRatingDto, productRating);
		productRatingRepository.save(productRating);
		
		return true;
	}

	@Override
	public boolean deleteProductRating(int id) {

		productRatingRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("productRating", "id", id)
				);
		productRatingRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ProductRating> fetchAllProductRatings() {
		
		return productRatingRepository.findAll();
	}



}
