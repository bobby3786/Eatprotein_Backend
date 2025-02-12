package com.catalog.service;

import java.util.List;

import com.catalog.dto.TagDto;
import com.catalog.entity.Tag;

public interface ITagService {

	void createTag(TagDto tagDto);
	Tag fetchTag(int id);
	boolean updateTag(TagDto tagDto);
	boolean deleteTag(int id);
	List<Tag> fetchAllTags();
	
}
