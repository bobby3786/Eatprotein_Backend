package com.catalog.service;

import java.util.List;

import com.catalog.dto.ProductDto;
import com.catalog.entity.Product;

public interface IProductService {
	
	void createProduct(ProductDto productDto);
	Product fetchProduct(int id);
	boolean updateProduct(ProductDto productDto);
	boolean deleteProduct(int id);
	List<Product> fetchAllProducts();
	List<Product> getProductsByFilter(Integer categoryId,Integer subcategoryId,Integer brandId);

}
