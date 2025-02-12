package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.ProductDto;
import com.catalog.entity.Category;
import com.catalog.entity.Product;
import com.catalog.entity.SubCategory;
import com.catalog.repository.CategoryRepository;
import com.catalog.repository.ProductRepository;
import com.catalog.repository.SubCategoryRepository;
import com.catalog.service.IProductService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
    private CategoryRepository categoryRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Override
	public void createProduct(ProductDto productDto) {

		Product product=new Product();
		ObjectEntityCheckutil.copyNonNullProperties(productDto, product);
		
		
//		Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
//				() -> new IllegalArgumentException("Invalid category ID")
//				);
//		
//		product.setCategoryId(category);
//		SubCategory subCategory = subCategoryRepository.findById(productDto.getSubcategoryId()).orElseThrow(
//				()-> new IllegalArgumentException("Invalid subCategory Id")
//				);
//		
//		product.setSubcategoryId(subCategory);
		
		productRepository.save(product);
		
	}

	@Override
	public Product fetchProduct(int id) {
	
		Product product = productRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Product", "id", id)
				);
		
		return product;
		
	}

	@Override
	public boolean updateProduct(ProductDto productDto) {
	
		Product product = productRepository.findById(productDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("Product", "id", productDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(productDto, product);
		productRepository.save(product);
		
		return true;
	}

	@Override
	public boolean deleteProduct(int id) {

	Product product = productRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Product", "id", id)
				);
	
		productRepository.delete(product);
		return true;
	}

	@Override
	public List<Product> fetchAllProducts() {
		
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByFilter(Integer categoryId,
			Integer subcategoryId, Integer brandId) {
		
		return productRepository.findByCategoryIdOrSubcategoryIdOrBrandId(categoryId, subcategoryId, brandId);
	}

}
