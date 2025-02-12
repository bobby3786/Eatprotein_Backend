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

import com.authentication.dto.UserPromocodeDto;
import com.authentication.entity.UserPromocode;
import com.authentication.service.IUserPromocodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public  class UserPromocodeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserPromocodeService iUserPromocodeService;
	
	  @Test
	    public void testCreateUserPromocode() throws Exception {

		  UserPromocodeDto userPromocodeDto = new UserPromocodeDto();
		  userPromocodeDto.setUserPhone("9182291822");
		  userPromocodeDto.setUserId(1);
		  
	        doNothing().when(iUserPromocodeService).createUserPromocode(userPromocodeDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/userpromocode/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userPromocodeDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserPromocodeDetails() throws Exception {

		  int id = 1;
	      UserPromocode userPromocode = new UserPromocode();
	      userPromocode.setId(id);
	      userPromocode.setUserPhone("9182291822");
	      userPromocode.setUserId(1);

	      when(iUserPromocodeService.fetchUserPromocode(id)).thenReturn(userPromocode);

	      mockMvc.perform(get("/auth/userpromocode/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.userPhone").value("9182291822"));
	  }
	  
	  @Test
	  public void testUpdateUserPromocodeDetails_Success() throws Exception {
	      
	      UserPromocodeDto userPromocodeDto = new UserPromocodeDto();
	      userPromocodeDto.setId(1);
	      userPromocodeDto.setUserPhone("9182291822");
		  userPromocodeDto.setUserId(1);

	      when(iUserPromocodeService.updateUserPromocode(userPromocodeDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/userpromocode/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userPromocodeDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserPromocodeDetails_Failure() throws Exception {
	      
	      UserPromocodeDto userPromocodeDto = new UserPromocodeDto();
	      userPromocodeDto.setId(1);
	      userPromocodeDto.setUserPhone("9182291822");
		  userPromocodeDto.setUserId(1);
	      
	      when(iUserPromocodeService.updateUserPromocode(userPromocodeDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/userpromocode/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userPromocodeDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserPromocodeDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUserPromocodeService.deleteUserPromocode(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/userpromocode/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserPromocodeDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUserPromocodeService.deleteUserPromocode(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/userpromocode/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserPromocodes() throws Exception {
	      UserPromocode userPromocode1 = new UserPromocode();
	      userPromocode1.setId(1);
	      userPromocode1.setUserPhone("9182291822");
	      userPromocode1.setUserId(1);

	      UserPromocode userPromocode2 = new UserPromocode();
	      userPromocode2.setId(2);
	      userPromocode2.setUserPhone("9182291822");
	      userPromocode2.setUserId(1);

	      List<UserPromocode> UserPromocodeList = Arrays.asList(userPromocode1, userPromocode2);
	      when(iUserPromocodeService.fetchAllUserPromocodes()).thenReturn(UserPromocodeList);

	      mockMvc.perform(get("/auth/userpromocode/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
