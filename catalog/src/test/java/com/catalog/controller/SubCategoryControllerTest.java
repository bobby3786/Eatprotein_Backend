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

import com.catalog.dto.SubCategoryDto;
import com.catalog.entity.SubCategory;
import com.catalog.service.ISubCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class SubCategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private ISubCategoryService iSubCategoryService;
	
	  @Test
	    public void testCreateSubCategory() throws Exception {

		  SubCategoryDto subCategoryDto = new SubCategoryDto();
		  subCategoryDto.setName("SEEDS");
		  
	        doNothing().when(iSubCategoryService).createSubCategory(subCategoryDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/subcategory/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(subCategoryDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchSubCategoryDetails() throws Exception {

		  int id = 1;
	      SubCategory subCategory = new SubCategory();
	      subCategory.setId(id);
	      subCategory.setName("SEEDS");

	      when(iSubCategoryService.fetchSubCategory(id)).thenReturn(subCategory);

	      mockMvc.perform(get("/catalog/subcategory/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("SEEDS")) ;
	      
	  }
	  
	  @Test
	  public void testUpdateSubCategoryDetails_Success() throws Exception {
	      
	      SubCategoryDto subCategoryDto = new SubCategoryDto();
	      subCategoryDto.setId(1);
	      subCategoryDto.setName("TIFFINS");

	      when(iSubCategoryService.updateSubCategory(subCategoryDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/subcategory/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(subCategoryDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateSubCategoryDetails_Failure() throws Exception {
	      
	      SubCategoryDto subCategoryDto = new SubCategoryDto();
	      subCategoryDto.setId(1);
	      subCategoryDto.setName("SEEDS");
	      
	      when(iSubCategoryService.updateSubCategory(subCategoryDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/subcategory/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(subCategoryDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteSubCategoryDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iSubCategoryService.deleteSubCategory(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/subcategory/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteSubCategoryDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iSubCategoryService.deleteSubCategory(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/subcategory/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllSubCategorys() throws Exception {
	      SubCategory subCategory1 = new SubCategory();
	      subCategory1.setId(1);
	      subCategory1.setName("SEEDS");

	      SubCategory subCategory2 = new SubCategory();
	      subCategory2.setId(2);
	      subCategory2.setName("TIFFINS");

	      List<SubCategory> SubCategoryList = Arrays.asList(subCategory1, subCategory2);
	      when(iSubCategoryService.fetchAllSubCategories()).thenReturn(SubCategoryList);

	      mockMvc.perform(get("/catalog/subcategory/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
