package com.authentication.controller;

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

import com.authentication.dto.UniqueCodeDto;
import com.authentication.entity.UniqueCode;
import com.authentication.service.IUniqueCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class UniqueCodeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUniqueCodeService iUniqueCodeService;
	
	  @Test
	    public void testCreateUniqueCode() throws Exception {

		  UniqueCodeDto uniqueCodeDto = new UniqueCodeDto();
		  uniqueCodeDto.setRefCode(456);
		  
	        doNothing().when(iUniqueCodeService).createUniqueCode(uniqueCodeDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/uniquecode/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(uniqueCodeDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUniqueCodeDetails() throws Exception {

		  int id = 1;
	      UniqueCode uniqueCode = new UniqueCode();
	      uniqueCode.setId(id);
	      uniqueCode.setRefCode(456);

	      when(iUniqueCodeService.fetchUniqueCode(id)).thenReturn(uniqueCode);

	      mockMvc.perform(get("/auth/uniquecode/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.refCode").value(456));
	  }
	  
	  @Test
	  public void testUpdateUniqueCodeDetails_Success() throws Exception {
	      
	      UniqueCodeDto uniqueCodeDto = new UniqueCodeDto();
	      uniqueCodeDto.setId(1);
	      uniqueCodeDto.setRefCode(456);
	      
	      when(iUniqueCodeService.updateUniqueCode(uniqueCodeDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/uniquecode/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(uniqueCodeDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUniqueCodeDetails_Failure() throws Exception {
	      
	      UniqueCodeDto uniqueCodeDto = new UniqueCodeDto();
	      uniqueCodeDto.setId(1);
	      uniqueCodeDto.setRefCode(456);
	      
	      when(iUniqueCodeService.updateUniqueCode(uniqueCodeDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/uniquecode/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(uniqueCodeDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUniqueCodeDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUniqueCodeService.deleteUniqueCode(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/uniquecode/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUniqueCodeDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUniqueCodeService.deleteUniqueCode(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/uniquecode/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUniqueCodes() throws Exception {
	      UniqueCode uniqueCode1 = new UniqueCode();
	      uniqueCode1.setId(1);
	      uniqueCode1.setRefCode(456);

	      UniqueCode uniqueCode2 = new UniqueCode();
	      uniqueCode2.setId(2);
	      uniqueCode2.setRefCode(456);

	      List<UniqueCode> UniqueCodeList = Arrays.asList(uniqueCode1, uniqueCode2);
	      when(iUniqueCodeService.fetchAllUniqueCodes()).thenReturn(UniqueCodeList);

	      mockMvc.perform(get("/auth/uniquecode/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }

	
}
