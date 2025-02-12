package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.SubCategoryDto;
import com.catalog.entity.Category;
import com.catalog.entity.SubCategory;
import com.catalog.repository.CategoryRepository;
import com.catalog.repository.SubCategoryRepository;
import com.catalog.service.ISubCategoryService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class SubCategoryServiceImpl implements ISubCategoryService{
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	

	@Override
	public void createSubCategory(SubCategoryDto subCategoryDto) {
		SubCategory subCategory=new SubCategory();
		ObjectEntityCheckutil.copyNonNullProperties(subCategoryDto, subCategory);
		subCategoryRepository.save(subCategory);
		
	}

	@Override
	public SubCategory fetchSubCategory(int id) {
		SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("SubCategory", "id", id)
				);
		
		return subCategory;
	}

	@Override
	public boolean updateSubCategory(SubCategoryDto subCategoryDto) {
		SubCategory subCategory = subCategoryRepository.findById(subCategoryDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("SubCategory", "id", subCategoryDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(subCategoryDto, subCategory);
		subCategoryRepository.save(subCategory);
		
		return true;
	}

	@Override
	public boolean deleteSubCategory(int id) {
	SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("SubCategory", "id", id)
				);
		subCategoryRepository.delete(subCategory);
		return true;
	}

	@Override
	public List<SubCategory> fetchAllSubCategories() {
		
		return subCategoryRepository.findAll();
	}

}
