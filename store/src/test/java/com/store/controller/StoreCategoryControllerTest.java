package com.store.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;
import com.store.dto.StoreCategoryDto;
import com.store.entity.StoreCategory;
import com.store.service.IStoreCategoryService;


@SpringBootTest
@AutoConfigureMockMvc
public class StoreCategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IStoreCategoryService iStoreCategoryService;
	
	  @Test
	    public void testCreateStoreCategory() throws Exception {

		  StoreCategoryDto storeCategoryDto = new StoreCategoryDto();
		  storeCategoryDto.setStatus("active");
		  storeCategoryDto.setCategoryId(1);
		  
	        doNothing().when(iStoreCategoryService).createStoreCategory(storeCategoryDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/store/storecategory/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(storeCategoryDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchStoreCategoryDetails() throws Exception {

		  String id = "1";
	      StoreCategory storeCategory = new StoreCategory();
	      storeCategory.setId(id);
	      storeCategory.setStatus("active");
	      storeCategory.setCategoryId(1);

	      when(iStoreCategoryService.fetchStoreCategory(id)).thenReturn(storeCategory);

	      mockMvc.perform(get("/store/storecategory/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("active"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").value(1));
	  }
	  
	  @Test
	  public void testUpdateStoreCategoryDetails_Success() throws Exception {
	      
	      StoreCategoryDto storeCategoryDto = new StoreCategoryDto();
	      storeCategoryDto.setId("1");
	      storeCategoryDto.setStatus("active");
		  storeCategoryDto.setCategoryId(1);

	      when(iStoreCategoryService.updateStoreCategory(storeCategoryDto)).thenReturn(true);

	      mockMvc.perform(put("/store/storecategory/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeCategoryDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateStoreCategoryDetails_Failure() throws Exception {
	      
	      StoreCategoryDto storeCategoryDto = new StoreCategoryDto();
	      storeCategoryDto.setId("1");
	      storeCategoryDto.setStatus("active");
		  storeCategoryDto.setCategoryId(1);
	      
	      when(iStoreCategoryService.updateStoreCategory(storeCategoryDto)).thenReturn(false);

	      mockMvc.perform(put("/store/storecategory/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeCategoryDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteStoreCategoryDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iStoreCategoryService.deleteStoreCategory(id)).thenReturn(true);

	      mockMvc.perform(delete("/store/storecategory/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteStoreCategoryDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iStoreCategoryService.deleteStoreCategory(id)).thenReturn(false);

	      mockMvc.perform(delete("/store/storecategory/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllStoreCategorys() throws Exception {
	      StoreCategory storeCategory1 = new StoreCategory();
	      storeCategory1.setId("1");
	      storeCategory1.setStatus("active");
	      storeCategory1.setCategoryId(1);

	      StoreCategory storeCategory2 = new StoreCategory();
	      storeCategory2.setId("2");
	      storeCategory2.setStatus("active");
	      storeCategory2.setCategoryId(1);

	      List<StoreCategory> StoreCategoryList = Arrays.asList(storeCategory1, storeCategory2);
	      when(iStoreCategoryService.fetchAllStoreCategories()).thenReturn(StoreCategoryList);

	      mockMvc.perform(get("/store/storecategory/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
