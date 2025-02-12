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

import com.catalog.dto.NewUserCouponUsageDto;
import com.catalog.entity.NewUserCouponUsage;
import com.catalog.service.INewUserCouponUsageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class NewUserCouponUsageControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private INewUserCouponUsageService iNewUserCouponUsageService;
	
	  @Test
	    public void testCreateNewUserCouponUsage() throws Exception {

		  NewUserCouponUsageDto newUserCouponUsageDto = new NewUserCouponUsageDto();
		  newUserCouponUsageDto.setPhone("9182291822");
		  newUserCouponUsageDto.setUserId(1);
		  newUserCouponUsageDto.setCouponCode("EATPROTEIN");
		  
	        doNothing().when(iNewUserCouponUsageService).createNewUserCouponUsage(newUserCouponUsageDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/nucu/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(newUserCouponUsageDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchNewUserCouponUsageDetails() throws Exception {

		  int id = 1;
	      NewUserCouponUsage newUserCouponUsage = new NewUserCouponUsage();
	      newUserCouponUsage.setId(id);
	      newUserCouponUsage.setPhone("9182291822");
	      newUserCouponUsage.setUserId(1);
	      newUserCouponUsage.setCouponCode("EATPROTEIN");

	      when(iNewUserCouponUsageService.fetchNewUserCouponUsage(id)).thenReturn(newUserCouponUsage);

	      mockMvc.perform(get("/catalog/nucu/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("9182291822"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.couponCode").value("EATPROTEIN"));
	      
	  }
	  
	  @Test
	  public void testUpdateNewUserCouponUsageDetails_Success() throws Exception {
	      
	      NewUserCouponUsageDto newUserCouponUsageDto = new NewUserCouponUsageDto();
	      newUserCouponUsageDto.setId(1);
	      newUserCouponUsageDto.setPhone("9182291833");
		  newUserCouponUsageDto.setUserId(1);
		  newUserCouponUsageDto.setCouponCode("EATPROTEIN");

	      when(iNewUserCouponUsageService.updateNewUserCouponUsage(newUserCouponUsageDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/nucu/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(newUserCouponUsageDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateNewUserCouponUsageDetails_Failure() throws Exception {
	      
	      NewUserCouponUsageDto newUserCouponUsageDto = new NewUserCouponUsageDto();
	      newUserCouponUsageDto.setId(1);
	      newUserCouponUsageDto.setPhone("9182291822");
		  newUserCouponUsageDto.setUserId(1);
		  newUserCouponUsageDto.setCouponCode("EATPROTEIN");
	      
	      when(iNewUserCouponUsageService.updateNewUserCouponUsage(newUserCouponUsageDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/nucu/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(newUserCouponUsageDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteNewUserCouponUsageDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iNewUserCouponUsageService.deleteNewUserCouponUsage(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/nucu/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteNewUserCouponUsageDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iNewUserCouponUsageService.deleteNewUserCouponUsage(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/nucu/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllNewUserCouponUsages() throws Exception {
	      NewUserCouponUsage newUserCouponUsage1 = new NewUserCouponUsage();
	      newUserCouponUsage1.setId(1);
	      newUserCouponUsage1.setPhone("9182291822");
	      newUserCouponUsage1.setUserId(1);
	      newUserCouponUsage1.setCouponCode("EATPROTEIN");

	      NewUserCouponUsage newUserCouponUsage2 = new NewUserCouponUsage();
	      newUserCouponUsage2.setId(2);
	      newUserCouponUsage1.setPhone("9182291833");
	      newUserCouponUsage1.setUserId(1);
	      newUserCouponUsage1.setCouponCode("EATPROTEIN");

	      List<NewUserCouponUsage> NewUserCouponUsageList = Arrays.asList(newUserCouponUsage1, newUserCouponUsage2);
	      when(iNewUserCouponUsageService.fetchAllNewUserCouponUsages()).thenReturn(NewUserCouponUsageList);

	      mockMvc.perform(get("/catalog/nucu/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
