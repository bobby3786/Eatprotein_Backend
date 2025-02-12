package com.catalog.service;

import java.util.List;

import com.catalog.dto.SubCategoryDto;
import com.catalog.entity.SubCategory;

public interface ISubCategoryService {
	
	void createSubCategory(SubCategoryDto subCategoryDto);
	SubCategory fetchSubCategory(int id);
	boolean updateSubCategory(SubCategoryDto subCategoryDto);
	boolean deleteSubCategory(int id);
	List<SubCategory> fetchAllSubCategories();

}
