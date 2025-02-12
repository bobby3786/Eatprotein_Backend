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

import com.authentication.dto.FcmAuthTokenDto;
import com.authentication.entity.FcmAuthToken;
import com.authentication.service.IFcmAuthTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class FcmAuthTokenControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IFcmAuthTokenService iFcmAuthTokenService;
	
	  @Test
	    public void testCreateFcmAuthToken() throws Exception {

		  FcmAuthTokenDto fcmAuthTokenDto = new FcmAuthTokenDto();
		  fcmAuthTokenDto.setToken("abcd");
		  fcmAuthTokenDto.setExpiresIn(5);
		  
	        doNothing().when(iFcmAuthTokenService).createFcmAuthToken(fcmAuthTokenDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/fat/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(fcmAuthTokenDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchFcmAuthTokenDetails() throws Exception {

		  int id = 1;
	      FcmAuthToken fcmAuthToken = new FcmAuthToken();
	      fcmAuthToken.setId(id);
	      fcmAuthToken.setToken("abcd");
	      fcmAuthToken.setExpiresIn(5);

	      when(iFcmAuthTokenService.fetchFcmAuthToken(id)).thenReturn(fcmAuthToken);

	      mockMvc.perform(get("/auth/fat/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("abcd"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.expiresIn").value(5));
	  }
	  
	  @Test
	  public void testUpdateFcmAuthTokenDetails_Success() throws Exception {
	      
	      FcmAuthTokenDto fcmAuthTokenDto = new FcmAuthTokenDto();
	      fcmAuthTokenDto.setId(1);
	      fcmAuthTokenDto.setToken("abcd");
		  fcmAuthTokenDto.setExpiresIn(5);

	      when(iFcmAuthTokenService.updateFcmAuthToken(fcmAuthTokenDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/fat/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(fcmAuthTokenDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateFcmAuthTokenDetails_Failure() throws Exception {
	      
	      FcmAuthTokenDto fcmAuthTokenDto = new FcmAuthTokenDto();
	      fcmAuthTokenDto.setId(1);
	      fcmAuthTokenDto.setToken("abcd");
		  fcmAuthTokenDto.setExpiresIn(5);
	      
	      when(iFcmAuthTokenService.updateFcmAuthToken(fcmAuthTokenDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/fat/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(fcmAuthTokenDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteFcmAuthTokenDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iFcmAuthTokenService.deleteFcmAuthToken(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/fat/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteFcmAuthTokenDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iFcmAuthTokenService.deleteFcmAuthToken(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/fat/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllFcmAuthTokens() throws Exception {
	      FcmAuthToken fcmAuthToken1 = new FcmAuthToken();
	      fcmAuthToken1.setId(1);
	      fcmAuthToken1.setToken("abcd");
	      fcmAuthToken1.setExpiresIn(5);

	      FcmAuthToken fcmAuthToken2 = new FcmAuthToken();
	      fcmAuthToken2.setId(2);
	      fcmAuthToken2.setToken("abcd");
	      fcmAuthToken2.setExpiresIn(5);

	      List<FcmAuthToken> FcmAuthTokenList = Arrays.asList(fcmAuthToken1, fcmAuthToken2);
	      when(iFcmAuthTokenService.fetchAllFcmAuthTokens()).thenReturn(FcmAuthTokenList);

	      mockMvc.perform(get("/auth/fat/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
