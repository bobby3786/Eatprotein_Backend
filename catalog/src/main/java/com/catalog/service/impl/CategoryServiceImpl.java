package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.CategoryDto;
import com.catalog.entity.Category;
import com.catalog.repository.CategoryRepository;
import com.catalog.service.ICategoryService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void createCategory(CategoryDto categoryDto) {

		Category category=new Category();
		ObjectEntityCheckutil.copyNonNullProperties(categoryDto, category);
		categoryRepository.save(category);
		
	}

	@Override
	public Category fetchCategory(int id) {
		Category category = categoryRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Category", "id", id)
				);
		
		return category;
	}

	@Override
	public boolean updateCategory(CategoryDto categoryDto) {
		Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("Category", "id", categoryDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(categoryDto, category);
		categoryRepository.save(category);
		
		return true;
	}

	@Override
	public boolean deleteCategory(int id) {
//		categoryRepository.findById(id).orElseThrow(
//				()-> new ResourceNotFoundException("Category", "id", id)
//				);
//		
//		categoryRepository.deleteById(id);
		
		 Category category = categoryRepository.findById(id)
		            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

		    // Delete the category (automatically deletes subcategories via cascade)
		    categoryRepository.delete(category);
		return true;
	}

	@Override
	public List<Category> fetchAllCategories() {
		return categoryRepository.findAll();
	}

}
