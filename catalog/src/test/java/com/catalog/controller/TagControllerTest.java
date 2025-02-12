package com.catalog.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.catalog.dto.TagDto;
import com.catalog.entity.Tag;
import com.catalog.service.ITagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private ITagService iTagService;
	
	  @Test
	    public void testCreateTag() throws Exception {

		  TagDto tagDto = new TagDto();
		  tagDto.setImagePath("tag/image.jpg");
		  
	        doNothing().when(iTagService).createTag(tagDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/tag/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(tagDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchTagDetails() throws Exception {

		  int id = 1;
	      Tag tag = new Tag();
	      tag.setId(id);
	      tag.setImagePath("tag/image.jpg");

	      when(iTagService.fetchTag(id)).thenReturn(tag);

	      mockMvc.perform(get("/catalog/tag/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.imagePath").value("tag/image.jpg")) ;
	      
	  }
	  
	  @Test
	  public void testUpdateTagDetails_Success() throws Exception {
	      
	      TagDto tagDto = new TagDto();
	      tagDto.setId(1);
	      tagDto.setImagePath("tag/image.jpg");

	      when(iTagService.updateTag(tagDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/tag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(tagDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateTagDetails_Failure() throws Exception {
	      
	      TagDto tagDto = new TagDto();
	      tagDto.setId(1);
	      tagDto.setImagePath("tag/image.jpg");
	      
	      when(iTagService.updateTag(tagDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/tag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(tagDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteTagDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iTagService.deleteTag(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/tag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteTagDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iTagService.deleteTag(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/tag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllTags() throws Exception {
	      Tag tag1 = new Tag();
	      tag1.setId(1);
	      tag1.setImagePath("tag/image.jpg");

	      Tag tag2 = new Tag();
	      tag2.setId(2);
	      tag2.setImagePath("tag/image1.jpg");

	      List<Tag> tagList = Arrays.asList(tag1, tag2);
	      when(iTagService.fetchAllTags()).thenReturn(tagList);

	      mockMvc.perform(get("/catalog/tag/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
