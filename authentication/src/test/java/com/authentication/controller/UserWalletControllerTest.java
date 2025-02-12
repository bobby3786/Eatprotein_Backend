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

import com.authentication.dto.UserWalletDto;
import com.authentication.entity.UserWallet;
import com.authentication.service.IUserWalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class UserWalletControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserWalletService iUserWalletService;
	
	  @Test
	    public void testCreateUserWallet() throws Exception {

		  UserWalletDto userWalletDto = new UserWalletDto();
		  userWalletDto.setUserId(2);
		  userWalletDto.setAvailableTotal("500");
		  
	        doNothing().when(iUserWalletService).createUserWallet(userWalletDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/userwallet/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userWalletDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserWalletDetails() throws Exception {

		  int id = 1;
	      UserWallet userWallet = new UserWallet();
	      userWallet.setId(id);
	      userWallet.setUserId(2);
	      userWallet.setAvailableTotal("500");

	      when(iUserWalletService.fetchUserWallet(id)).thenReturn(userWallet);

	      mockMvc.perform(get("/auth/userwallet/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.availableTotal").value("500"));
	  }
	  
	  @Test
	  public void testUpdateUserWalletDetails_Success() throws Exception {
	      
	      UserWalletDto userWalletDto = new UserWalletDto();
	      userWalletDto.setId(1);
	      userWalletDto.setUserId(2);
		  userWalletDto.setAvailableTotal("500");

	      when(iUserWalletService.updateUserWallet(userWalletDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/userwallet/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userWalletDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserWalletDetails_Failure() throws Exception {
	      
	      UserWalletDto userWalletDto = new UserWalletDto();
	      userWalletDto.setId(1);
	      userWalletDto.setUserId(2);
		  userWalletDto.setAvailableTotal("500");
	      
	      when(iUserWalletService.updateUserWallet(userWalletDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/userwallet/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userWalletDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserWalletDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUserWalletService.deleteUserWallet(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/userwallet/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserWalletDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUserWalletService.deleteUserWallet(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/userwallet/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserWallets() throws Exception {
	      UserWallet userWallet1 = new UserWallet();
	      userWallet1.setId(1);
	      userWallet1.setUserId(2);
	      userWallet1.setAvailableTotal("500");

	      UserWallet userWallet2 = new UserWallet();
	      userWallet2.setId(2);
	      userWallet2.setUserId(2);
	      userWallet2.setAvailableTotal("500");

	      List<UserWallet> UserWalletList = Arrays.asList(userWallet1, userWallet2);
	      when(iUserWalletService.fetchAllUserWallets()).thenReturn(UserWalletList);

	      mockMvc.perform(get("/auth/userwallet/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
