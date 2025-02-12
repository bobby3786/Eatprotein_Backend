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
import com.store.dto.StoreProductDto;
import com.store.entity.StoreProduct;
import com.store.service.IStoreProductService;


@SpringBootTest
@AutoConfigureMockMvc
public class StoreProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IStoreProductService iStoreProductService;
	
	  @Test
	    public void testCreateStoreProduct() throws Exception {

		  StoreProductDto storeProductDto = new StoreProductDto();
		  storeProductDto.setAvgRating(5);
		  storeProductDto.setStoreId(1);
		  
	        doNothing().when(iStoreProductService).createStoreProduct(storeProductDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/store/storeproduct/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(storeProductDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchStoreProductDetails() throws Exception {

		  String id = "1";
	      StoreProduct storeProduct = new StoreProduct();
	      storeProduct.setId(id);
	      storeProduct.setAvgRating(5);
	      storeProduct.setStoreId(1);

	      when(iStoreProductService.fetchStoreProduct(id)).thenReturn(storeProduct);

	      mockMvc.perform(get("/store/storeproduct/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.avgRating").value(5))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.storeId").value(1));
	  }
	  
	  @Test
	  public void testUpdateStoreProductDetails_Success() throws Exception {
	      
	      StoreProductDto storeProductDto = new StoreProductDto();
	      storeProductDto.setId("1");
	      storeProductDto.setAvgRating(5);
		  storeProductDto.setStoreId(1);

	      when(iStoreProductService.updateStoreProduct(storeProductDto)).thenReturn(true);

	      mockMvc.perform(put("/store/storeproduct/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeProductDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateStoreProductDetails_Failure() throws Exception {
	      
	      StoreProductDto storeProductDto = new StoreProductDto();
	      storeProductDto.setId("1");
	      storeProductDto.setAvgRating(5);
		  storeProductDto.setStoreId(1);
	      
	      when(iStoreProductService.updateStoreProduct(storeProductDto)).thenReturn(false);

	      mockMvc.perform(put("/store/storeproduct/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeProductDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteStoreProductDetails_Success() throws Exception {
	      
	      String id = "1";
	      when(iStoreProductService.deleteStoreProduct(id)).thenReturn(true);

	      mockMvc.perform(delete("/store/storeproduct/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteStoreProductDetails_Failure() throws Exception {
	      
	      String id = "1";
	      when(iStoreProductService.deleteStoreProduct(id)).thenReturn(false);

	      mockMvc.perform(delete("/store/storeproduct/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllStoreProducts() throws Exception {
	      StoreProduct storeProduct1 = new StoreProduct();
	      storeProduct1.setId("1");
	      storeProduct1.setAvgRating(5);
	      storeProduct1.setStoreId(1);

	      StoreProduct storeProduct2 = new StoreProduct();
	      storeProduct2.setId("2");
	      storeProduct2.setAvgRating(5);
	      storeProduct2.setStoreId(1);
		  
		  
	      List<StoreProduct> StoreProductList = Arrays.asList(storeProduct1, storeProduct2);
	      when(iStoreProductService.fetchAllStoreProducts()).thenReturn(StoreProductList);

	      mockMvc.perform(get("/store/storeproduct/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }

	
}
