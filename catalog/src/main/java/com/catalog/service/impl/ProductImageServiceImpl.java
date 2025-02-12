package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.ProductImageDto;
import com.catalog.entity.Product;
import com.catalog.entity.ProductImage;
import com.catalog.repository.ProductImageRepository;
import com.catalog.repository.ProductRepository;
import com.catalog.service.IProductImageService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class ProductImageServiceImpl implements IProductImageService{
	
	@Autowired
	private ProductImageRepository productImageRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public void createProductImage(ProductImageDto productImageDto) {

		ProductImage productImage=new ProductImage();
		ObjectEntityCheckutil.copyNonNullProperties(productImageDto, productImage);
		
//	Product product = productRepository.findById(productImageDto.getProductId()).orElseThrow(
//				()-> new IllegalArgumentException("invalid product id")
//				);
//	productImage.setProductId(product);
		productImageRepository.save(productImage);
		
	}

	@Override
	public ProductImage fetchProductImage(int id) {
	
		ProductImage productImage = productImageRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("ProductImage", "id", id)
				);
		
		return productImage;
		
	}

	@Override
	public boolean updateProductImage(ProductImageDto productImageDto) {
	
		ProductImage productImage = productImageRepository.findById(productImageDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("Product", "id", productImageDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(productImageDto, productImage);
		productImageRepository.save(productImage);
		
		return true;
	}

	@Override
	public boolean deleteProductImage(int id) {

		productImageRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("ProductImage", "id", id)
				);
		productImageRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ProductImage> fetchAllProductImages() {
		
		return productImageRepository.findAll();
	}


}
