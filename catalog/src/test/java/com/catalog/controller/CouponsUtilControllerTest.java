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

import com.catalog.dto.CouponsUtilDto;
import com.catalog.entity.CouponsUtil;
import com.catalog.service.ICouponsUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class CouponsUtilControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private ICouponsUtilService iCouponsUtilService;
	
	  @Test
	    public void testCreateCouponsUtil() throws Exception {

		  CouponsUtilDto couponsUtilDto = new CouponsUtilDto();
		  couponsUtilDto.setExpireDays(10);
		  couponsUtilDto.setMaxLimit(100);
		  couponsUtilDto.setReferralPercentage(40);
		  couponsUtilDto.setReferredPercentage(30);
		  
	        doNothing().when(iCouponsUtilService).createCouponsUtil(couponsUtilDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/couponsutil/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(couponsUtilDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchCouponsUtilDetails() throws Exception {

		  int id = 1;
	      CouponsUtil couponsUtil = new CouponsUtil();
	      couponsUtil.setId(id);
	      
	      couponsUtil.setExpireDays(10);
		  couponsUtil.setMaxLimit(100);
		  couponsUtil.setReferralPercentage(40);
		  couponsUtil.setReferredPercentage(30);

	      when(iCouponsUtilService.fetchCouponsUtil(id)).thenReturn(couponsUtil);

	      mockMvc.perform(get("/catalog/couponsutil/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.expireDays").value(10))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.maxLimit").value(100))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.referralPercentage").value(40))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.referredPercentage").value(30));
	      
	  }
	  
	  @Test
	  public void testUpdateCouponsUtilDetails_Success() throws Exception {
	      
	      CouponsUtilDto couponsUtilDto = new CouponsUtilDto();
	      couponsUtilDto.setId(1);
	      couponsUtilDto.setExpireDays(10);
		  couponsUtilDto.setMaxLimit(100);
		  couponsUtilDto.setReferralPercentage(40);
		  couponsUtilDto.setReferredPercentage(30);

	      when(iCouponsUtilService.updateCouponsUtil(couponsUtilDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/couponsutil/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(couponsUtilDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateCouponsUtilDetails_Failure() throws Exception {
	      
	      CouponsUtilDto couponsUtilDto = new CouponsUtilDto();
	      couponsUtilDto.setId(1);
	      couponsUtilDto.setExpireDays(10);
		  couponsUtilDto.setMaxLimit(100);
		  couponsUtilDto.setReferralPercentage(40);
		  couponsUtilDto.setReferredPercentage(30);
	      
	      when(iCouponsUtilService.updateCouponsUtil(couponsUtilDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/couponsutil/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(couponsUtilDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteCouponsUtilDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iCouponsUtilService.deleteCouponsUtil(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/couponsutil/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteCouponsUtilDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iCouponsUtilService.deleteCouponsUtil(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/couponsutil/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllCouponsUtils() throws Exception {
	      CouponsUtil couponsUtil1 = new CouponsUtil();
	      couponsUtil1.setId(1);
	      couponsUtil1.setExpireDays(10);
		  couponsUtil1.setMaxLimit(100);
		  couponsUtil1.setReferralPercentage(40);
		  couponsUtil1.setReferredPercentage(30);

	      CouponsUtil couponsUtil2 = new CouponsUtil();
	      couponsUtil2.setId(2);
	      couponsUtil2.setExpireDays(10);
		  couponsUtil2.setMaxLimit(100);
		  couponsUtil2.setReferralPercentage(40);
		  couponsUtil2.setReferredPercentage(30);


	      List<CouponsUtil> CouponsUtilList = Arrays.asList(couponsUtil1, couponsUtil2);
	      when(iCouponsUtilService.fetchAllCouponsUtils()).thenReturn(CouponsUtilList);

	      mockMvc.perform(get("/catalog/couponsutil/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
