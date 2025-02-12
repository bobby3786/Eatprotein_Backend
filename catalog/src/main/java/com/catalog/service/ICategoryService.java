package com.catalog.service;

import java.util.List;

import com.catalog.dto.CategoryDto;
import com.catalog.entity.Category;

public interface ICategoryService {
	
	void createCategory(CategoryDto categoryDto);
	Category fetchCategory(int id);
	boolean updateCategory(CategoryDto categoryDto);
	boolean deleteCategory(int id);
	List<Category> fetchAllCategories();

}
