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

import com.catalog.dto.PromocodeDto;
import com.catalog.entity.Promocode;
import com.catalog.service.IPromocodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class PromocodeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IPromocodeService iPromocodeService;
	
	  @Test
	    public void testCreatePromocode() throws Exception {

		  PromocodeDto promocodeDto = new PromocodeDto();
		  promocodeDto.setImagePath("Promocode/image.jpg");
		  
	        doNothing().when(iPromocodeService).createPromocode(promocodeDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/promocode/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(promocodeDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchPromocodeDetails() throws Exception {

		  int id = 1;
	      Promocode promocode = new Promocode();
	      promocode.setId(id);
	      promocode.setImagePath("Promocode/image.jpg");

	      when(iPromocodeService.fetchPromocode(id)).thenReturn(promocode);

	      mockMvc.perform(get("/catalog/promocode/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.imagePath").value("Promocode/image.jpg")) ;
	      
	  }
	  
	  @Test
	  public void testUpdatePromocodeDetails_Success() throws Exception {
	      
	      PromocodeDto promocodeDto = new PromocodeDto();
	      promocodeDto.setId(1);
	      promocodeDto.setImagePath("Promocode/image.jpg");

	      when(iPromocodeService.updatePromocode(promocodeDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/promocode/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(promocodeDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdatePromocodeDetails_Failure() throws Exception {
	      
	      PromocodeDto promocodeDto = new PromocodeDto();
	      promocodeDto.setId(1);
	      promocodeDto.setImagePath("Promocode/image.jpg");
	      
	      when(iPromocodeService.updatePromocode(promocodeDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/promocode/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(promocodeDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeletePromocodeDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iPromocodeService.deletePromocode(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/promocode/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeletePromocodeDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iPromocodeService.deletePromocode(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/promocode/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllPromocodes() throws Exception {
	      Promocode promocode1 = new Promocode();
	      promocode1.setId(1);
	      promocode1.setImagePath("Promocode/image.jpg");

	      Promocode promocode2 = new Promocode();
	      promocode2.setId(2);
	      promocode2.setImagePath("Promocode/image1.jpg");

	      List<Promocode> PromocodeList = Arrays.asList(promocode1, promocode2);
	      when(iPromocodeService.fetchAllPromocodes()).thenReturn(PromocodeList);

	      mockMvc.perform(get("/catalog/promocode/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
