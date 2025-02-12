package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.ProductTagDto;
import com.catalog.entity.ProductTag;
import com.catalog.repository.ProductTagRepository;
import com.catalog.service.IProductTagService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class ProductTagServiceImpl implements IProductTagService{
	
	@Autowired
	private ProductTagRepository productTagRepository;

	@Override
	public void createProductTag(ProductTagDto productTagDto) {

		ProductTag productTag=new ProductTag();
		ObjectEntityCheckutil.copyNonNullProperties(productTagDto, productTag);
		productTagRepository.save(productTag);
		
	}

	@Override
	public ProductTag fetchProductTag(int id) {
	
		ProductTag productTag = productTagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("productTag", "id", id)
				);
		
		return productTag;
		
	}

	@Override
	public boolean updateProductTag(ProductTagDto productTagDto) {
	
		ProductTag productTag = productTagRepository.findById(productTagDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("ProductTag", "id", productTagDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(productTagDto, productTag);
		productTagRepository.save(productTag);
		
		return true;
	}

	@Override
	public boolean deleteProductTag(int id) {

		productTagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("ProductTag", "id", id)
				);
		productTagRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ProductTag> fetchAllProductTags() {
		
		return productTagRepository.findAll();
	}



}
