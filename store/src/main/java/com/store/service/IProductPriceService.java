package com.store.service;

import java.util.List;

import com.store.dto.ProductPriceDto;
import com.store.entity.ProductPrice;


public interface IProductPriceService {
	
	void createProductPrice(ProductPriceDto productPriceDto);
	ProductPrice fetchProductPrice(String id);
	boolean updateProductPrice(ProductPriceDto productPriceDto);
	boolean deleteProductPrice(String id);
	List<ProductPrice> fetchAllProductPrices();

}
