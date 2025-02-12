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

import com.catalog.dto.ProductDto;
import com.catalog.entity.Product;
import com.catalog.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IProductService iProductService;
	
	  @Test
	    public void testCreateProduct() throws Exception {

		  ProductDto productDto = new ProductDto();
		  productDto.setCategoryId(1);
		  productDto.setProductName("MOBILE");
		  
	        doNothing().when(iProductService).createProduct(productDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/product/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(productDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchProductDetails() throws Exception {

		  int id = 1;
	      Product product = new Product();
	      product.setId(id);
	      product.setProductName("MOBILE");

	      when(iProductService.fetchProduct(id)).thenReturn(product);

	      mockMvc.perform(get("/catalog/product/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("MOBILE")) ;
	      
	  }
	  
	  @Test
	  public void testUpdateProductDetails_Success() throws Exception {
	      
	      ProductDto productDto = new ProductDto();
	      productDto.setId(1);
		  productDto.setProductName("MOBILE");

	      when(iProductService.updateProduct(productDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/product/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateProductDetails_Failure() throws Exception {
	      
	      ProductDto productDto = new ProductDto();
	      productDto.setId(1);
		  productDto.setProductName("MOBILE");
	      
	      when(iProductService.updateProduct(productDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/product/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteProductDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iProductService.deleteProduct(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/product/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteProductDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iProductService.deleteProduct(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/product/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllProducts() throws Exception {
	      Product product1 = new Product();
	      product1.setId(1);
	      product1.setProductName("MOBILE");

	      Product product2 = new Product();
	      product2.setId(2);
	      product2.setProductName("LAPTOP");

	      List<Product> productList = Arrays.asList(product1, product2);
	      when(iProductService.fetchAllProducts()).thenReturn(productList);

	      mockMvc.perform(get("/catalog/product/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
