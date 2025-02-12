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

import com.authentication.dto.UserDeletedDto;
import com.authentication.entity.UserDeleted;
import com.authentication.service.IUserDeletedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class UserDeletedControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserDeletedService iUserDeletedService;
	
	  @Test
	    public void testCreateUserDeleted() throws Exception {

		  UserDeletedDto userDeletedDto = new UserDeletedDto();
		  userDeletedDto.setPhone("9182291822");
		  
	        doNothing().when(iUserDeletedService).createUserDeleted(userDeletedDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/userdeleted/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userDeletedDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserDeletedDetails() throws Exception {

		  int id = 1;
	      UserDeleted userDeleted = new UserDeleted();
	      userDeleted.setId(id);
	      userDeleted.setPhone("9182291822");

	      when(iUserDeletedService.fetchUserDeleted(id)).thenReturn(userDeleted);

	      mockMvc.perform(get("/auth/userdeleted/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("9182291822"));
	  }
	  
	  @Test
	  public void testUpdateUserDeletedDetails_Success() throws Exception {
	      
	      UserDeletedDto userDeletedDto = new UserDeletedDto();
	      userDeletedDto.setId(1);
	      userDeletedDto.setPhone("9182291822");

	      when(iUserDeletedService.updateUserDeleted(userDeletedDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/userdeleted/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userDeletedDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserDeletedDetails_Failure() throws Exception {
	      
	      UserDeletedDto userDeletedDto = new UserDeletedDto();
	      userDeletedDto.setId(1);
	      userDeletedDto.setPhone("9182291822");
	      
	      when(iUserDeletedService.updateUserDeleted(userDeletedDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/userdeleted/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userDeletedDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserDeletedDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUserDeletedService.deleteUserDeleted(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/userdeleted/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserDeletedDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUserDeletedService.deleteUserDeleted(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/userdeleted/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserDeleteds() throws Exception {
	      UserDeleted userDeleted1 = new UserDeleted();
	      userDeleted1.setId(1);
	      userDeleted1.setPhone("9182291822");

	      UserDeleted userDeleted2 = new UserDeleted();
	      userDeleted2.setId(2);
	      userDeleted2.setPhone("9182291822");

	      List<UserDeleted> UserDeletedList = Arrays.asList(userDeleted1, userDeleted2);
	      when(iUserDeletedService.fetchAllUsersDeleted()).thenReturn(UserDeletedList);

	      mockMvc.perform(get("/auth/userdeleted/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
