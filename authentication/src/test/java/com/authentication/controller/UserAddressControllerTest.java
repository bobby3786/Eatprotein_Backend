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

import com.authentication.dto.UserAddressDto;
import com.authentication.entity.UserAddress;
import com.authentication.service.IUserAddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class UserAddressControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserAddressService iUserAddressService;
	
	  @Test
	    public void testCreateUserAddress() throws Exception {

		  UserAddressDto userAddressDto = new UserAddressDto();
		  userAddressDto.setName("Bobby");
		  userAddressDto.setCity("kavali");
		  
	        doNothing().when(iUserAddressService).createUserAddress(userAddressDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/useraddress/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userAddressDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserAddressDetails() throws Exception {

		  int id = 1;
	      UserAddress userAddress = new UserAddress();
	      userAddress.setId(id);
	      userAddress.setName("Bobby");
	      userAddress.setCity("kavali");

	      when(iUserAddressService.fetchUserAddress(id)).thenReturn(userAddress);

	      mockMvc.perform(get("/auth/useraddress/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bobby"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("kavali"));
	  }
	  
	  @Test
	  public void testUpdateUserAddressDetails_Success() throws Exception {
	      
	      UserAddressDto userAddressDto = new UserAddressDto();
	      userAddressDto.setId(1);
	      userAddressDto.setName("Bobby");
		  userAddressDto.setCity("kavali");

	      when(iUserAddressService.updateUserAddress(userAddressDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/useraddress/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userAddressDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserAddressDetails_Failure() throws Exception {
	      
	      UserAddressDto userAddressDto = new UserAddressDto();
	      userAddressDto.setId(1);
	      userAddressDto.setName("Bobby");
		  userAddressDto.setCity("kavali");
	      
	      when(iUserAddressService.updateUserAddress(userAddressDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/useraddress/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userAddressDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserAddressDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUserAddressService.deleteUserAddress(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/useraddress/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserAddressDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUserAddressService.deleteUserAddress(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/useraddress/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserAddresss() throws Exception {
	      UserAddress userAddress1 = new UserAddress();
	      userAddress1.setId(1);
	      userAddress1.setName("Bobby");
	      userAddress1.setCity("kavali");

	      UserAddress userAddress2 = new UserAddress();
	      userAddress2.setId(2);
	      userAddress2.setName("Bobby");
	      userAddress2.setCity("kavali");

	      List<UserAddress> UserAddressList = Arrays.asList(userAddress1, userAddress2);
	      when(iUserAddressService.fetchAllUserAddresses()).thenReturn(UserAddressList);

	      mockMvc.perform(get("/auth/useraddress/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
