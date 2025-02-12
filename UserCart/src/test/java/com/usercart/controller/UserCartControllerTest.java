package com.usercart.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;
import com.usercart.dto.UserCartDto;
import com.usercart.entity.UserCart;
import com.usercart.service.IUserCartService;



@SpringBootTest
@AutoConfigureMockMvc
public class UserCartControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserCartService iUserCartService;
	
	  @Test
	    public void testCreateUserCart() throws Exception {

		  UserCartDto userCartDto = new UserCartDto();
		  userCartDto.setPriceId(1);
		  userCartDto.setQuantity(10);
		  
	        doNothing().when(iUserCartService).createUserCart(userCartDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/usercart/usercart/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userCartDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserCartDetails() throws Exception {

		  int id = 1;
	      UserCart userCart = new UserCart();
	      userCart.setId(id);
	      userCart.setPriceId(1);
	      userCart.setQuantity(10);

	      when(iUserCartService.fetchUserCart(id)).thenReturn(userCart);

	      mockMvc.perform(get("/usercart/usercart/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.priceId").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10));
	  }
	  
	  @Test
	  public void testUpdateUserCartDetails_Success() throws Exception {
	      
	      UserCartDto userCartDto = new UserCartDto();
	      userCartDto.setId(1);
	      userCartDto.setPriceId(1);
		  userCartDto.setQuantity(10);

	      when(iUserCartService.updateUserCart(userCartDto)).thenReturn(true);

	      mockMvc.perform(put("/usercart/usercart/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userCartDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserCartDetails_Failure() throws Exception {
	      
	      UserCartDto userCartDto = new UserCartDto();
	      userCartDto.setId(1);
	      userCartDto.setPriceId(1);
		  userCartDto.setQuantity(10);
	      
	      when(iUserCartService.updateUserCart(userCartDto)).thenReturn(false);

	      mockMvc.perform(put("/usercart/usercart/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userCartDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserCartDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUserCartService.deleteUserCart(id)).thenReturn(true);

	      mockMvc.perform(delete("/usercart/usercart/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserCartDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUserCartService.deleteUserCart(id)).thenReturn(false);

	      mockMvc.perform(delete("/usercart/usercart/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserCarts() throws Exception {
	      UserCart userCart1 = new UserCart();
	      userCart1.setId(1);
	      userCart1.setPriceId(1);
	      userCart1.setQuantity(10);

	      UserCart userCart2 = new UserCart();
	      userCart2.setId(2);
	      userCart2.setPriceId(1);
	      userCart2.setQuantity(10);

	      List<UserCart> UserCartList = Arrays.asList(userCart1, userCart2);
	      when(iUserCartService.fetchAllUserCarts()).thenReturn(UserCartList);

	      mockMvc.perform(get("/usercart/usercart/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
