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

import com.catalog.dto.BrandDto;
import com.catalog.entity.Brand;
import com.catalog.service.IBrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class BrandControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IBrandService iBrandService;
	
	  @Test
	    public void testCreateBrand() throws Exception {

		  BrandDto brandDto = new BrandDto();
		  brandDto.setStatus("Active");
		  
	        doNothing().when(iBrandService).createBrand(brandDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/brand/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(brandDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchBrandDetails() throws Exception {

		  int id = 1;
	      Brand brand = new Brand();
	      brand.setId(id);
	      brand.setStatus("Active");

	      when(iBrandService.fetchBrand(id)).thenReturn(brand);

	      mockMvc.perform(get("/catalog/brand/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Active"));
	  }
	  
	  @Test
	  public void testUpdateBrandDetails_Success() throws Exception {
	      
	      BrandDto brandDto = new BrandDto();
	      brandDto.setId(1);
	      brandDto.setStatus("Active");

	      when(iBrandService.updateBrand(brandDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/brand/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(brandDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateBrandDetails_Failure() throws Exception {
	      
	      BrandDto brandDto = new BrandDto();
	      brandDto.setId(1);
	      brandDto.setStatus("Active");
	      
	      when(iBrandService.updateBrand(brandDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/brand/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(brandDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteBrandDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iBrandService.deleteBrand(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/brand/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteBrandDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iBrandService.deleteBrand(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/brand/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllBrands() throws Exception {
	      Brand brand1 = new Brand();
	      brand1.setId(1);
	      brand1.setStatus("Active");

	      Brand brand2 = new Brand();
	      brand2.setId(2);
	      brand2.setStatus("InActive");

	      List<Brand> brandList = Arrays.asList(brand1, brand2);
	      when(iBrandService.fetchAllBrands()).thenReturn(brandList);

	      mockMvc.perform(get("/catalog/brand/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
