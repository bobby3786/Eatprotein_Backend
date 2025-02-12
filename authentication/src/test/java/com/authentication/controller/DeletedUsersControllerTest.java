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

import com.authentication.dto.DeletedUsersDto;
import com.authentication.entity.DeletedUsers;
import com.authentication.service.IDeletedUsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class DeletedUsersControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IDeletedUsersService iDeletedUsersService;
	
	  @Test
	    public void testCreateDeletedUsers() throws Exception {

		  DeletedUsersDto deletedUsersDto = new DeletedUsersDto();
		  deletedUsersDto.setName("xyz");
		  deletedUsersDto.setNumber("123");
		  
	        doNothing().when(iDeletedUsersService).createDeletedUsers(deletedUsersDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/deletedusers/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(deletedUsersDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchDeletedUsersDetails() throws Exception {

		  int id = 1;
	      DeletedUsers deletedUsers = new DeletedUsers();
	      deletedUsers.setId(id);
	      deletedUsers.setName("xyz");
	      deletedUsers.setNumber("123");

	      when(iDeletedUsersService.fetchDeletedUsers(id)).thenReturn(deletedUsers);

	      mockMvc.perform(get("/auth/deletedusers/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("xyz"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("123"));
	  }
	  
	  @Test
	  public void testUpdateDeletedUsersDetails_Success() throws Exception {
	      
	      DeletedUsersDto deletedUsersDto = new DeletedUsersDto();
	      deletedUsersDto.setId(1);
	      deletedUsersDto.setName("xyz");
		  deletedUsersDto.setNumber("123");

	      when(iDeletedUsersService.updateDeletedUsers(deletedUsersDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/deletedusers/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(deletedUsersDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateDeletedUsersDetails_Failure() throws Exception {
	      
	      DeletedUsersDto deletedUsersDto = new DeletedUsersDto();
	      deletedUsersDto.setId(1);
	      deletedUsersDto.setName("xyz");
		  deletedUsersDto.setNumber("123");
	      
	      when(iDeletedUsersService.updateDeletedUsers(deletedUsersDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/deletedusers/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(deletedUsersDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteDeletedUsersDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iDeletedUsersService.deleteDeletedUsers(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/deletedusers/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteDeletedUsersDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iDeletedUsersService.deleteDeletedUsers(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/deletedusers/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllDeletedUserss() throws Exception {
	      DeletedUsers deletedUsers1 = new DeletedUsers();
	      deletedUsers1.setId(1);
	      deletedUsers1.setName("xyz");
	      deletedUsers1.setNumber("123");

	      DeletedUsers deletedUsers2 = new DeletedUsers();
	      deletedUsers2.setId(2);
	      deletedUsers2.setName("xyz");
	      deletedUsers2.setNumber("123");

	      List<DeletedUsers> DeletedUsersList = Arrays.asList(deletedUsers1, deletedUsers2);
	      when(iDeletedUsersService.fetchAllDeletedUsers()).thenReturn(DeletedUsersList);

	      mockMvc.perform(get("/auth/deletedusers/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
