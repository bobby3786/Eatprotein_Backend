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
import com.orders.dto.OrderLogDto;
import com.orders.entity.OrderLog;
import com.orders.service.IOrderLogService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderLogControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IOrderLogService iOrderLogService;
	
	  @Test
	    public void testCreateOrderLog() throws Exception {

		  OrderLogDto orderLogDto = new OrderLogDto();
		  orderLogDto.setStoreId(10);
		  orderLogDto.setOrderId(10);
		  
	        doNothing().when(iOrderLogService).createOrderLog(orderLogDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/orders/orderlog/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(orderLogDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchOrderLogDetails() throws Exception {

		  String id = "1";
	      OrderLog orderLog = new OrderLog();
	      orderLog.setId(id);
	      orderLog.setStoreId(10);
	      orderLog.setOrderId(10);

	      when(iOrderLogService.fetchOrderLog(id)).thenReturn(orderLog);

	      mockMvc.perform(get("/orders/orderlog/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.storeId").value(10))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(10));
	  }
	  
	  @Test
	  public void testUpdateOrderLogDetails_Success() throws Exception {
	      
	      OrderLogDto orderLogDto = new OrderLogDto();
	      orderLogDto.setId("1");
	      orderLogDto.setStoreId(10);
		  orderLogDto.setOrderId(10);

	      when(iOrderLogService.updateOrderLog(orderLogDto)).thenReturn(true);

	      mockMvc.perform(put("/orders/orderlog/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderLogDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateOrderLogDetails_Failure() throws Exception {
	      
	      OrderLogDto orderLogDto = new OrderLogDto();
	      orderLogDto.setId("1");
	      orderLogDto.setStoreId(10);
		  orderLogDto.setOrderId(10);
	      
	      when(iOrderLogService.updateOrderLog(orderLogDto)).thenReturn(false);

	      mockMvc.perform(put("/orders/orderlog/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderLogDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteOrderLogDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iOrderLogService.deleteOrderLog(id)).thenReturn(true);

	      mockMvc.perform(delete("/orders/orderlog/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteOrderLogDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iOrderLogService.deleteOrderLog(id)).thenReturn(false);

	      mockMvc.perform(delete("/orders/orderlog/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllOrderLogs() throws Exception {
	      OrderLog orderLog1 = new OrderLog();
	      orderLog1.setId("1");
	      orderLog1.setStoreId(10);
	      orderLog1.setOrderId(10);

	      OrderLog orderLog2 = new OrderLog();
	      orderLog2.setId("2");
	      orderLog2.setStoreId(10);
	      orderLog2.setOrderId(10);

	      List<OrderLog> OrderLogList = Arrays.asList(orderLog1, orderLog2);
	      when(iOrderLogService.fetchAllOrderLogs()).thenReturn(OrderLogList);

	      mockMvc.perform(get("/orders/orderlog/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
