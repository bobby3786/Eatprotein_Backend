package com.orders.controller;

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
import com.orders.dto.UserOrderDto;
import com.orders.entity.UserOrder;
import com.orders.service.IUserOrderService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class UserOrderControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserOrderService iUserOrderService;
	
	  @Test
	    public void testCreateUserOrder() throws Exception {

		  UserOrderDto userOrderDto = new UserOrderDto();
		  userOrderDto.setOrderStatus(1);
		  userOrderDto.setAppAmount(500);
		  
	        doNothing().when(iUserOrderService).createUserOrder(userOrderDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/orders/userorder/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userOrderDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserOrderDetails() throws Exception {

		  String id = "1";
	      UserOrder userOrder = new UserOrder();
	      userOrder.setId(id);
	      userOrder.setOrderStatus(1);
	      userOrder.setAppAmount(500);

	      when(iUserOrderService.fetchUserOrder(id)).thenReturn(userOrder);

	      mockMvc.perform(get("/orders/userorder/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.appAmount").value(500));
	  }
	  
	  @Test
	  public void testUpdateUserOrderDetails_Success() throws Exception {
	      
	      UserOrderDto userOrderDto = new UserOrderDto();
	      userOrderDto.setId("1");
	      userOrderDto.setOrderStatus(1);
		  userOrderDto.setAppAmount(500);

	      when(iUserOrderService.updateUserOrder(userOrderDto)).thenReturn(true);

	      mockMvc.perform(put("/orders/userorder/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userOrderDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserOrderDetails_Failure() throws Exception {
	      
	      UserOrderDto userOrderDto = new UserOrderDto();
	      userOrderDto.setId("1");
	      userOrderDto.setOrderStatus(1);
		  userOrderDto.setAppAmount(500);
	      
	      when(iUserOrderService.updateUserOrder(userOrderDto)).thenReturn(false);

	      mockMvc.perform(put("/orders/userorder/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userOrderDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserOrderDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iUserOrderService.deleteUserOrder(id)).thenReturn(true);

	      mockMvc.perform(delete("/orders/userorder/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserOrderDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iUserOrderService.deleteUserOrder(id)).thenReturn(false);

	      mockMvc.perform(delete("/orders/userorder/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserOrders() throws Exception {
	      UserOrder userOrder1 = new UserOrder();
	      userOrder1.setId("1");
	      userOrder1.setOrderStatus(1);
	      userOrder1.setAppAmount(500);

	      UserOrder userOrder2 = new UserOrder();
	      userOrder2.setId("2");
	      userOrder2.setOrderStatus(1);
	      userOrder2.setAppAmount(500);

	      List<UserOrder> userOrderList = Arrays.asList(userOrder1, userOrder2);
	      when(iUserOrderService.fetchAllUserOrders()).thenReturn(userOrderList);

	      mockMvc.perform(get("/orders/userorder/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
