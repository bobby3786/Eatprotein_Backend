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
import com.orders.dto.OrderAddressDto;
import com.orders.entity.OrderAddress;
import com.orders.service.IOrderAddressService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderAddressControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IOrderAddressService iOrderAddressService;
	
	  @Test
	    public void testCreateOrderAddress() throws Exception {

		  OrderAddressDto orderAddressDto = new OrderAddressDto();
		  orderAddressDto.setAddress("kavali");
		  orderAddressDto.setOrderId(1);
		  
	        doNothing().when(iOrderAddressService).createOrderAddress(orderAddressDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/orders/orderaddress/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(orderAddressDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchOrderAddressDetails() throws Exception {

		  String id = "1";
	      OrderAddress orderAddress = new OrderAddress();
	      orderAddress.setId(id);
	      orderAddress.setAddress("kavali");
	      orderAddress.setOrderId(1);

	      when(iOrderAddressService.fetchOrderAddress(id)).thenReturn(orderAddress);

	      mockMvc.perform(get("/orders/orderaddress/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("kavali"));
	  }
	  
	  @Test
	  public void testUpdateOrderAddressDetails_Success() throws Exception {
	      
	      OrderAddressDto orderAddressDto = new OrderAddressDto();
	      orderAddressDto.setId("1");
	      orderAddressDto.setAddress("kavali");
		  orderAddressDto.setOrderId(1);

	      when(iOrderAddressService.updateOrderAddress(orderAddressDto)).thenReturn(true);

	      mockMvc.perform(put("/orders/orderaddress/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderAddressDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateOrderAddressDetails_Failure() throws Exception {
	      
	      OrderAddressDto orderAddressDto = new OrderAddressDto();
	      orderAddressDto.setId("1");
	      orderAddressDto.setAddress("kavali");
		  orderAddressDto.setOrderId(1);
	      
	      when(iOrderAddressService.updateOrderAddress(orderAddressDto)).thenReturn(false);

	      mockMvc.perform(put("/orders/orderaddress/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderAddressDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteOrderAddressDetails_Success() throws Exception {
	      
	      String id = "1";
	      when(iOrderAddressService.deleteOrderAddress(id)).thenReturn(true);

	      mockMvc.perform(delete("/orders/orderaddress/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteOrderAddressDetails_Failure() throws Exception {
	      
	      String id = "1";
	      when(iOrderAddressService.deleteOrderAddress(id)).thenReturn(false);

	      mockMvc.perform(delete("/orders/orderaddress/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllOrderAddresss() throws Exception {
	      OrderAddress orderAddress1 = new OrderAddress();
	      orderAddress1.setId("1");
	      orderAddress1.setAddress("kavali");
	      orderAddress1.setOrderId(1);

	      OrderAddress orderAddress2 = new OrderAddress();
	      orderAddress2.setId("2");
	      orderAddress2.setAddress("kavali");
	      orderAddress2.setOrderId(1);

	      List<OrderAddress> OrderAddressList = Arrays.asList(orderAddress1, orderAddress2);
	      when(iOrderAddressService.fetchAllOrderAddresses()).thenReturn(OrderAddressList);

	      mockMvc.perform(get("/orders/orderaddress/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
