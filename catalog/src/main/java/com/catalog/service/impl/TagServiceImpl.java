package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.TagDto;
import com.catalog.entity.Tag;
import com.catalog.repository.TagRepository;
import com.catalog.service.ITagService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class TagServiceImpl implements ITagService{
	
	@Autowired
	private TagRepository tagRepository;

	@Override
	public void createTag(TagDto tagDto) {

		Tag tag=new Tag();
		ObjectEntityCheckutil.copyNonNullProperties(tagDto, tag);
		tagRepository.save(tag);
		
	}

	@Override
	public Tag fetchTag(int id) {
	
		Tag tag = tagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Tag", "id", id)
				);
		
		return tag;
		
	}

	@Override
	public boolean updateTag(TagDto tagdto) {
	
		Tag tag = tagRepository.findById(tagdto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("Tag", "id", tagdto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(tagdto, tag);
		tagRepository.save(tag);
		
		return true;
	}

	@Override
	public boolean deleteTag(int id) {

		tagRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Tag", "id", id)
				);
		tagRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Tag> fetchAllTags() {
		
		return tagRepository.findAll();
	}


}
