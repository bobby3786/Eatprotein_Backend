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

import com.catalog.dto.UomDto;
import com.catalog.entity.Uom;
import com.catalog.service.IUomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class UomControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUomService iUomService;
	
	  @Test
	    public void testCreateUom() throws Exception {

		  UomDto uomDto = new UomDto();
		  uomDto.setName("xyz");
		  
	        doNothing().when(iUomService).createUom(uomDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/uom/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(uomDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUomDetails() throws Exception {

		  int id = 1;
	      Uom uom = new Uom();
	      uom.setId(id);
	      uom.setName("xyz");

	      when(iUomService.fetchUom(id)).thenReturn(uom);

	      mockMvc.perform(get("/catalog/uom/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("xyz")) ;
	      
	  }
	  
	  @Test
	  public void testUpdateUomDetails_Success() throws Exception {
	      
	      UomDto uomDto = new UomDto();
	      uomDto.setId(1);
	      uomDto.setName("xyz");

	      when(iUomService.updateUom(uomDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/uom/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(uomDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUomDetails_Failure() throws Exception {
	      
	      UomDto uomDto = new UomDto();
	      uomDto.setId(1);
	      uomDto.setName("xyz");
	      
	      when(iUomService.updateUom(uomDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/uom/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(uomDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUomDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUomService.deleteUom(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/uom/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUomDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUomService.deleteUom(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/uom/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUoms() throws Exception {
	      Uom uom1 = new Uom();
	      uom1.setId(1);
	      uom1.setName("xyz");

	      Uom uom2 = new Uom();
	      uom2.setId(2);
	      uom2.setName("abc");

	      List<Uom> UomList = Arrays.asList(uom1, uom2);
	      when(iUomService.fetchAllUoms()).thenReturn(UomList);

	      mockMvc.perform(get("/catalog/uom/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
