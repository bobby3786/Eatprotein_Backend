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

import com.catalog.dto.ProductTagDto;
import com.catalog.entity.ProductTag;
import com.catalog.service.IProductTagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductTagControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IProductTagService iProductTagService;
	
	  @Test
	    public void testCreateProductTag() throws Exception {

		  ProductTagDto productTagDto = new ProductTagDto();
		  productTagDto.setTagId(1);
		  
	        doNothing().when(iProductTagService).createProductTag(productTagDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/producttag/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(productTagDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchProductTagDetails() throws Exception {

		  int id = 1;
	      ProductTag productTag = new ProductTag();
	      productTag.setId(id);
	      productTag.setTagId(1);

	      when(iProductTagService.fetchProductTag(id)).thenReturn(productTag);

	      mockMvc.perform(get("/catalog/producttag/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.tagId").value(1)) ;
	      
	  }
	  
	  @Test
	  public void testUpdateProductTagDetails_Success() throws Exception {
	      
	      ProductTagDto productTagDto = new ProductTagDto();
	      productTagDto.setId(1);
	      productTagDto.setTagId(1);

	      when(iProductTagService.updateProductTag(productTagDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/producttag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(productTagDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateProductTagDetails_Failure() throws Exception {
	      
	      ProductTagDto ProductTagDto = new ProductTagDto();
	      ProductTagDto.setId(1);
	      ProductTagDto.setTagId(1);
	      
	      when(iProductTagService.updateProductTag(ProductTagDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/producttag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(ProductTagDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteProductTagDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iProductTagService.deleteProductTag(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/producttag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteProductTagDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iProductTagService.deleteProductTag(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/producttag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllProductTags() throws Exception {
	      ProductTag productTag1 = new ProductTag();
	      productTag1.setId(1);
	      productTag1.setTagId(1);

	      ProductTag productTag2 = new ProductTag();
	      productTag2.setId(2);
	      productTag2.setTagId(2);

	      List<ProductTag> ProductTagList = Arrays.asList(productTag1, productTag2);
	      when(iProductTagService.fetchAllProductTags()).thenReturn(ProductTagList);

	      mockMvc.perform(get("/catalog/producttag/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
