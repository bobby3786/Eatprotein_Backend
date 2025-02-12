package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.dto.ProductPriceDto;
import com.store.entity.ProductPrice;
import com.store.repository.ProductPriceRepository;
import com.store.service.IProductPriceService;


@Service
public class ProductPriceServiceImpl implements IProductPriceService{
	
	@Autowired
	private ProductPriceRepository productPriceRepository;

	@Override
	public void createProductPrice(ProductPriceDto productPriceDto) {

		ProductPrice productPrice=new ProductPrice();
		ObjectEntityCheckutil.copyNonNullProperties(productPriceDto, productPrice);
		productPriceRepository.save(productPrice);
		
	}

	@Override
	public ProductPrice fetchProductPrice(String id) {
	
		ProductPrice productPrice = productPriceRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("productPrice", "id", id)
				);
		
		return productPrice;
		
	}

	@Override
	public boolean updateProductPrice(ProductPriceDto productPriceDto) {
	
		ProductPrice productPrice = productPriceRepository.findById(productPriceDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("ProductPrice", "id", productPriceDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(productPriceDto, productPrice);
		productPriceRepository.save(productPrice);
		
		return true;
	}

	@Override
	public boolean deleteProductPrice(String id) {

		productPriceRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("ProductPrice", "id", id)
				);
		productPriceRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ProductPrice> fetchAllProductPrices() {
		
		return productPriceRepository.findAll();
	}



}
