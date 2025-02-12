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

import com.catalog.dto.ProductImageDto;
import com.catalog.entity.ProductImage;
import com.catalog.service.IProductImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductImageControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IProductImageService iProductImageService;
	
	  @Test
	    public void testCreateProductImage() throws Exception {

		  ProductImageDto productImageDto = new ProductImageDto();
		  productImageDto.setImagePath("product/image.jpg");
		  
	        doNothing().when(iProductImageService).createProductImage(productImageDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/productimage/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(productImageDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchProductImageDetails() throws Exception {

		  int id = 1;
	      ProductImage productImage = new ProductImage();
	      productImage.setId(id);
	      productImage.setImagePath("product/image.jpg");

	      when(iProductImageService.fetchProductImage(id)).thenReturn(productImage);

	      mockMvc.perform(get("/catalog/productimage/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.imagePath").value("product/image.jpg")) ;
	      
	  }
	  
	  @Test
	  public void testUpdateProductImageDetails_Success() throws Exception {
	      
	      ProductImageDto productImageDto = new ProductImageDto();
	      productImageDto.setId(1);
	      productImageDto.setImagePath("product/image.jpg");

	      when(iProductImageService.updateProductImage(productImageDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/productimage/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productImageDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateProductImageDetails_Failure() throws Exception {
	      
	      ProductImageDto productImageDto = new ProductImageDto();
	      productImageDto.setId(1);
	      productImageDto.setImagePath("product/image.jpg");
	      
	      when(iProductImageService.updateProductImage(productImageDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/productimage/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productImageDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteProductImageDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iProductImageService.deleteProductImage(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/productimage/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteProductImageDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iProductImageService.deleteProductImage(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/productimage/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllProductImages() throws Exception {
	      ProductImage productImage1 = new ProductImage();
	      productImage1.setId(1);
	      productImage1.setImagePath("product/image.jpg");

	      ProductImage productImage2 = new ProductImage();
	      productImage2.setId(2);
	      productImage2.setImagePath("product/image1.jpg");

	      List<ProductImage> productImageList = Arrays.asList(productImage1, productImage2);
	      when(iProductImageService.fetchAllProductImages()).thenReturn(productImageList);

	      mockMvc.perform(get("/catalog/productimage/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }




}
