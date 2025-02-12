package com.catalog.service;

import java.util.List;

import com.catalog.dto.ProductImageDto;
import com.catalog.entity.ProductImage;

public interface IProductImageService {
	
	void createProductImage(ProductImageDto productImageDto);
	ProductImage fetchProductImage(int id);
	boolean updateProductImage(ProductImageDto productImageDto);
	boolean deleteProductImage(int id);
	List<ProductImage> fetchAllProductImages();

}
