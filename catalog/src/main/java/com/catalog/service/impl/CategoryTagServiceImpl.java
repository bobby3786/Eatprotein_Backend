package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.CategoryTagDto;
import com.catalog.entity.CategoryTag;
import com.catalog.repository.CategoryTagRepository;
import com.catalog.service.ICategoryTagService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class CategoryTagServiceImpl implements ICategoryTagService{
	
	@Autowired
	private CategoryTagRepository categoryTagRepository;

	@Override
	public void createCategoryTag(CategoryTagDto categoryTagDto) {

		CategoryTag categoryTag=new CategoryTag();
		ObjectEntityCheckutil.copyNonNullProperties(categoryTagDto, categoryTag);
		categoryTagRepository.save(categoryTag);
		
	}

	@Override
	public CategoryTag fetchCategoryTag(int id) {
	
		CategoryTag categoryTag = categoryTagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("categoryTag", "id", id)
				);
		
		return categoryTag;
		
	}

	@Override
	public boolean updateCategoryTag(CategoryTagDto categoryTagDto) {
	
		CategoryTag categoryTag = categoryTagRepository.findById(categoryTagDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("categoryTag", "id", categoryTagDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(categoryTagDto, categoryTag);
		categoryTagRepository.save(categoryTag);
		
		return true;
	}

	@Override
	public boolean deleteCategoryTag(int id) {

		categoryTagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("CategoryTag", "id", id)
				);
		categoryTagRepository.deleteById(id);
		return true;
	}

	@Override
	public List<CategoryTag> fetchAllCategoryTags() {
		
		return categoryTagRepository.findAll();
	}



}
