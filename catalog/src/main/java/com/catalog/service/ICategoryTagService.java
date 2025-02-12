package com.catalog.service;

import java.util.List;

import com.catalog.dto.CategoryTagDto;
import com.catalog.entity.CategoryTag;

public interface ICategoryTagService {
	
	void createCategoryTag(CategoryTagDto categoryTagDto);
	CategoryTag fetchCategoryTag(int id);
	boolean updateCategoryTag(CategoryTagDto categoryTagDto);
	boolean deleteCategoryTag(int id);
	List<CategoryTag> fetchAllCategoryTags();

}
