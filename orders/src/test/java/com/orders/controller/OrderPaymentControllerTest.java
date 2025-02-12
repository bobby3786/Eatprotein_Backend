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
import com.orders.dto.OrderPaymentDto;
import com.orders.entity.OrderPayment;
import com.orders.service.IOrderPaymentService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderPaymentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IOrderPaymentService iOrderPaymentService;
	
	  @Test
	    public void testCreateOrderPayment() throws Exception {

		  OrderPaymentDto orderPaymentDto = new OrderPaymentDto();
		  orderPaymentDto.setAmount(500);
		  orderPaymentDto.setOrderId(1);
		  
	        doNothing().when(iOrderPaymentService).createOrderPayment(orderPaymentDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/orders/orderpayment/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(orderPaymentDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchOrderPaymentDetails() throws Exception {

		  String id = "1";
	      OrderPayment orderPayment = new OrderPayment();
	      orderPayment.setId(id);
	      orderPayment.setAmount(500);
	      orderPayment.setOrderId(1);

	      when(iOrderPaymentService.fetchOrderPayment(id)).thenReturn(orderPayment);

	      mockMvc.perform(get("/orders/orderpayment/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(1));
	  }
	  
	  @Test
	  public void testUpdateOrderPaymentDetails_Success() throws Exception {
	      
	      OrderPaymentDto orderPaymentDto = new OrderPaymentDto();
	      orderPaymentDto.setId("1");
	      orderPaymentDto.setAmount(500);
		  orderPaymentDto.setOrderId(1);

	      when(iOrderPaymentService.updateOrderPayment(orderPaymentDto)).thenReturn(true);

	      mockMvc.perform(put("/orders/orderpayment/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderPaymentDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateOrderPaymentDetails_Failure() throws Exception {
	      
	      OrderPaymentDto orderPaymentDto = new OrderPaymentDto();
	      orderPaymentDto.setId("1");
	      orderPaymentDto.setAmount(500);
		  orderPaymentDto.setOrderId(1);
	      
	      when(iOrderPaymentService.updateOrderPayment(orderPaymentDto)).thenReturn(false);

	      mockMvc.perform(put("/orders/orderpayment/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderPaymentDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteOrderPaymentDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iOrderPaymentService.deleteOrderPayment(id)).thenReturn(true);

	      mockMvc.perform(delete("/orders/orderpayment/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteOrderPaymentDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iOrderPaymentService.deleteOrderPayment(id)).thenReturn(false);

	      mockMvc.perform(delete("/orders/orderpayment/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllOrderPayments() throws Exception {
	      OrderPayment orderPayment1 = new OrderPayment();
	      orderPayment1.setId("1");
	      orderPayment1.setAmount(500);
	      orderPayment1.setOrderId(1);

	      OrderPayment orderPayment2 = new OrderPayment();
	      orderPayment2.setId("2");
	      orderPayment2.setAmount(500);
	      orderPayment2.setOrderId(1);
		  
	      List<OrderPayment> OrderPaymentList = Arrays.asList(orderPayment1, orderPayment2);
	      when(iOrderPaymentService.fetchAllOrderPayments()).thenReturn(OrderPaymentList);

	      mockMvc.perform(get("/orders/orderpayment/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
