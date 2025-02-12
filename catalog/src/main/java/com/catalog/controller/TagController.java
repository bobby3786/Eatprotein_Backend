package com.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catalog.dto.TagDto;
import com.catalog.entity.Tag;
import com.catalog.service.ITagService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("catalog/tag")
public class TagController {


	@Autowired
	private ITagService iTagService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createTag(@RequestBody TagDto tagDto){
		iTagService.createTag(tagDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Tag> fetchTagDetails(@RequestParam int id){
		
		Tag tag = iTagService.fetchTag(id);
	return ResponseEntity.status(HttpStatus.OK).body(tag);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateTagDetails(@RequestBody TagDto tagDto){
		
		boolean isUpdated = iTagService.updateTag(tagDto);
		if(isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
			
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500, Constants.MESSAGE_500));
			
		}
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteTagDetails(@RequestParam int id){
		
		boolean isDeleted = iTagService.deleteTag(id);
		if(isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
			
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500, Constants.MESSAGE_500));
			
		}
		
	}

	@GetMapping("/All")
	public ApiResponse<List<Tag>> getAllTags(){
	List<Tag> tag =	iTagService.fetchAllTags();
	
	return new ApiResponse<List<Tag>>("200", "success", tag);
	}


	
}
