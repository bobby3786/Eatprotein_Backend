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

import com.catalog.dto.ProductRatingDto;
import com.catalog.entity.ProductRating;
import com.catalog.service.IProductRatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductRatingControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IProductRatingService iProductRatingService;
	
	  @Test
	    public void testCreateProductRating() throws Exception {

		  ProductRatingDto productRatingDto = new ProductRatingDto();
		  productRatingDto.setRating(5);
		  
	        doNothing().when(iProductRatingService).createProductRating(productRatingDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/productrating/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(productRatingDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchProductRatingDetails() throws Exception {

		  int id = 1;
	      ProductRating productRating = new ProductRating();
	      productRating.setId(id);
	      productRating.setRating(5);

	      when(iProductRatingService.fetchProductRating(id)).thenReturn(productRating);

	      mockMvc.perform(get("/catalog/productrating/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(5)) ;
	      
	  }
	  
	  @Test
	  public void testUpdateProductRatingDetails_Success() throws Exception {
	      
	      ProductRatingDto productRatingDto = new ProductRatingDto();
	      productRatingDto.setId(1);
	      productRatingDto.setRating(5);

	      when(iProductRatingService.updateProductRating(productRatingDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/productrating/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productRatingDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateProductRatingDetails_Failure() throws Exception {
	      
	      ProductRatingDto productRatingDto = new ProductRatingDto();
	      productRatingDto.setId(1);
	      productRatingDto.setRating(5);
	      
	      when(iProductRatingService.updateProductRating(productRatingDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/productrating/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productRatingDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteProductRatingDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iProductRatingService.deleteProductRating(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/productrating/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteProductRatingDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iProductRatingService.deleteProductRating(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/productrating/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllProductRatings() throws Exception {
	      ProductRating productRating1 = new ProductRating();
	      productRating1.setId(1);
	      productRating1.setRating(5);

	      ProductRating productRating2 = new ProductRating();
	      productRating2.setId(2);
	      productRating2.setRating(5);

	      List<ProductRating> ProductRatingList = Arrays.asList(productRating1, productRating2);
	      when(iProductRatingService.fetchAllProductRatings()).thenReturn(ProductRatingList);

	      mockMvc.perform(get("/catalog/productrating/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
