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

import com.catalog.dto.CategoryDto;
import com.catalog.entity.Category;
import com.catalog.service.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private ICategoryService iCategoryService;
	
	  @Test
	    public void testCreateCategory() throws Exception {

		  CategoryDto categoryDto = new CategoryDto();
		  categoryDto.setStatus("Active");
		  
	        doNothing().when(iCategoryService).createCategory(categoryDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/category/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(categoryDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchCategoryDetails() throws Exception {

		  int id = 1;
	      Category category = new Category();
//	      category.setId(id);
//	      category.setStatus("Active");

	      when(iCategoryService.fetchCategory(id)).thenReturn(category);

	      mockMvc.perform(get("/catalog/category/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk());
//	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Active"));
	  }
	  
	  @Test
	  public void testUpdateCategoryDetails_Success() throws Exception {
	      
	      CategoryDto categoryDto = new CategoryDto();
	      categoryDto.setId(2);
	      categoryDto.setStatus("Active");

	      when(iCategoryService.updateCategory(categoryDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/category/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(categoryDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateCategoryDetails_Failure() throws Exception {
	      
	      CategoryDto categoryDto = new CategoryDto();
	      categoryDto.setId(1);
	      categoryDto.setStatus("Active");
	      
	      when(iCategoryService.updateCategory(categoryDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/category/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(categoryDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteCategoryDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iCategoryService.deleteCategory(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/category/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteCategoryDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iCategoryService.deleteCategory(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/category/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllCategories() throws Exception {
	      Category category1 = new Category();
	      category1.setId(1);
	      category1.setName("veg");
	  

	      Category category2 = new Category();
	      category2.setId(2);
	      category2.setName("NonVeg");

	      List<Category> categoryList = Arrays.asList(category1, category2);
	      when(iCategoryService.fetchAllCategories()).thenReturn(categoryList);

	      mockMvc.perform(get("/catalog/category/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}

