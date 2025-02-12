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

import com.catalog.dto.CategoryTagDto;
import com.catalog.entity.CategoryTag;
import com.catalog.service.ICategoryTagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTagControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private ICategoryTagService iCategoryTagService;
	
	  @Test
	    public void testCreateCategoryTag() throws Exception {

		  CategoryTagDto categoryTagDto = new CategoryTagDto();
		  categoryTagDto.setCategoryId(1);
		  categoryTagDto.setTagId(1);
		  
	        doNothing().when(iCategoryTagService).createCategoryTag(categoryTagDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/categorytag/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(categoryTagDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchCategoryTagDetails() throws Exception {

		  int id = 1;
	      CategoryTag categoryTag = new CategoryTag();
	      categoryTag.setId(id);
	      categoryTag.setCategoryId(1);
	      categoryTag.setTagId(1);

	      when(iCategoryTagService.fetchCategoryTag(id)).thenReturn(categoryTag);

	      mockMvc.perform(get("/catalog/categorytag/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.tagId").value(1)) ;
	      
	  }
	  
	  @Test
	  public void testUpdateCategoryTagDetails_Success() throws Exception {
	      
	      CategoryTagDto categoryTagDto = new CategoryTagDto();
	      categoryTagDto.setId(1);
	      categoryTagDto.setCategoryId(2);
	      categoryTagDto.setTagId(2);

	      when(iCategoryTagService.updateCategoryTag(categoryTagDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/categorytag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(categoryTagDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateCategoryTagDetails_Failure() throws Exception {
	      
	      CategoryTagDto categoryTagDto = new CategoryTagDto();
	      categoryTagDto.setId(1);
	      categoryTagDto.setCategoryId(2);
	      categoryTagDto.setTagId(1);
	      
	      when(iCategoryTagService.updateCategoryTag(categoryTagDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/categorytag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(categoryTagDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteCategoryTagDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iCategoryTagService.deleteCategoryTag(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/categorytag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteCategoryTagDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iCategoryTagService.deleteCategoryTag(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/categorytag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllCategoryTags() throws Exception {
	      CategoryTag categoryTag1 = new CategoryTag();
	      categoryTag1.setId(1);
	      categoryTag1.setCategoryId(1);
	      categoryTag1.setTagId(1);

	      CategoryTag categoryTag2 = new CategoryTag();
	      categoryTag2.setId(2);
	      categoryTag2.setCategoryId(1);
	      categoryTag2.setTagId(1);

	      List<CategoryTag> categoryTagList = Arrays.asList(categoryTag1, categoryTag2);
	      when(iCategoryTagService.fetchAllCategoryTags()).thenReturn(categoryTagList);

	      mockMvc.perform(get("/catalog/categorytag/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}


