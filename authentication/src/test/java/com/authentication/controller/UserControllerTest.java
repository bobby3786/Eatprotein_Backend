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

import com.authentication.dto.UserDto;
import com.authentication.entity.User;
import com.authentication.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserService iUserService;
	
	  @Test
	    public void testCreateUser() throws Exception {

		  UserDto userDto = new UserDto();
		  userDto.setName("Bobby");
		  userDto.setCity("kavali");
		  
	        doNothing().when(iUserService).createUser(userDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/user/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserDetails() throws Exception {

		  int id = 1;
	      User user = new User();
	      user.setId(id);
	      user.setName("Bobby");
	      user.setCity("kavali");

	      when(iUserService.fetchUser(id)).thenReturn(user);

	      mockMvc.perform(get("/auth/user/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bobby"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("kavali"));
	  }
	  
	  @Test
	  public void testUpdateUserDetails_Success() throws Exception {
	      
	      UserDto userDto = new UserDto();
	      userDto.setId(1);
	      userDto.setName("Bobby");
		  userDto.setCity("kavali");

	      when(iUserService.updateUser(userDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/user/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserDetails_Failure() throws Exception {
	      
	      UserDto userDto = new UserDto();
	      userDto.setId(1);
	      userDto.setName("Bobby");
		  userDto.setCity("kavali");
	      
	      when(iUserService.updateUser(userDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/user/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUserService.deleteUser(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/user/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUserService.deleteUser(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/user/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUsers() throws Exception {
	      User user1 = new User();
	      user1.setId(1);
	      user1.setName("Bobby");
	      user1.setCity("kavali");

	      User user2 = new User();
	      user2.setId(2);
	      user2.setName("Bobby");
	      user2.setCity("kavali");

	      List<User> UserList = Arrays.asList(user1, user2);
	      when(iUserService.fetchAllUsers()).thenReturn(UserList);

	      mockMvc.perform(get("/auth/user/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
