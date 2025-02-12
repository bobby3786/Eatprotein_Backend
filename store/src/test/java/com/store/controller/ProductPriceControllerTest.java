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
import com.store.dto.ProductPriceDto;
import com.store.entity.ProductPrice;
import com.store.service.IProductPriceService;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductPriceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private IProductPriceService iProductPriceService;
	
	  @Test
	    public void testCreateProductPrice() throws Exception {

		  ProductPriceDto productPriceDto = new ProductPriceDto();
		  productPriceDto.setStatus("Active");
		  
	        doNothing().when(iProductPriceService).createProductPrice(productPriceDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/productprice/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(productPriceDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchProductPriceDetails() throws Exception {

		  String id = "1";
	      ProductPrice productPrice = new ProductPrice();
	      productPrice.setId(id);
	      productPrice.setStatus("ACTIVE");

	      when(iProductPriceService.fetchProductPrice(id)).thenReturn(productPrice);

	      mockMvc.perform(get("/catalog/productprice/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE")) ;
	      
	  }
	  
	  @Test
	  public void testUpdateProductPriceDetails_Success() throws Exception {
	      
	      ProductPriceDto productPriceDto = new ProductPriceDto();
	      productPriceDto.setId("1");
	      productPriceDto.setStatus("Active");

	      when(iProductPriceService.updateProductPrice(productPriceDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/productprice/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productPriceDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateProductPriceDetails_Failure() throws Exception {
	      
	      ProductPriceDto productPriceDto = new ProductPriceDto();
	      productPriceDto.setId("1");
	      productPriceDto.setStatus("Active");
	      
	      when(iProductPriceService.updateProductPrice(productPriceDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/productprice/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productPriceDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteProductPriceDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iProductPriceService.deleteProductPrice(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/productprice/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteProductPriceDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iProductPriceService.deleteProductPrice(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/productprice/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllProductPrices() throws Exception {
	      ProductPrice productPrice1 = new ProductPrice();
	      productPrice1.setId("1");
	      productPrice1.setStatus("Active");

	      ProductPrice productPrice2 = new ProductPrice();
	      productPrice2.setId("2");
	      productPrice2.setStatus("Active");

	      List<ProductPrice> ProductPriceList = Arrays.asList(productPrice1, productPrice2);
	      when(iProductPriceService.fetchAllProductPrices()).thenReturn(ProductPriceList);

	      mockMvc.perform(get("/catalog/productprice/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
