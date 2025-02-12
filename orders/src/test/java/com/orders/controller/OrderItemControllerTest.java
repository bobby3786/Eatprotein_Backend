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
import com.orders.dto.OrderItemDto;
import com.orders.entity.OrderItem;
import com.orders.service.IOrderItemService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderItemControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IOrderItemService iOrderItemService;
	
	  @Test
	    public void testCreateOrderItem() throws Exception {

		  OrderItemDto orderItemDto = new OrderItemDto();
		  orderItemDto.setPrice(500);
		  orderItemDto.setOrderId(1);
		  
	        doNothing().when(iOrderItemService).createOrderItem(orderItemDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/orders/orderitem/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(orderItemDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchOrderItemDetails() throws Exception {

		  String id = "1";
	      OrderItem orderItem = new OrderItem();
	      orderItem.setId(id);
	      orderItem.setPrice(500);
	      orderItem.setOrderId(1);

	      when(iOrderItemService.fetchOrderItem(id)).thenReturn(orderItem);

	      mockMvc.perform(get("/orders/orderitem/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(1));
	  }
	  
	  @Test
	  public void testUpdateOrderItemDetails_Success() throws Exception {
	      
	      OrderItemDto orderItemDto = new OrderItemDto();
	      orderItemDto.setId("1");
	      orderItemDto.setPrice(500);
		  orderItemDto.setOrderId(1);

	      when(iOrderItemService.updateOrderItem(orderItemDto)).thenReturn(true);

	      mockMvc.perform(put("/orders/orderitem/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderItemDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateOrderItemDetails_Failure() throws Exception {
	      
	      OrderItemDto orderItemDto = new OrderItemDto();
	      orderItemDto.setId("1");
	      orderItemDto.setPrice(500);
		  orderItemDto.setOrderId(1);
	      
	      when(iOrderItemService.updateOrderItem(orderItemDto)).thenReturn(false);

	      mockMvc.perform(put("/orders/orderitem/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderItemDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteOrderItemDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iOrderItemService.deleteOrderItem(id)).thenReturn(true);

	      mockMvc.perform(delete("/orders/orderitem/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteOrderItemDetails_Failure() throws Exception {
	      
		  String id =  "1";
	      when(iOrderItemService.deleteOrderItem(id)).thenReturn(false);

	      mockMvc.perform(delete("/orders/orderitem/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllOrderItems() throws Exception {
	      OrderItem orderItem1 = new OrderItem();
	      orderItem1.setId("1");
	      orderItem1.setPrice(500);
	      orderItem1.setOrderId(1);

	      OrderItem orderItem2 = new OrderItem();
	      orderItem2.setId("2");
	      orderItem2.setPrice(500);
	      orderItem2.setOrderId(1);

	      List<OrderItem> OrderItemList = Arrays.asList(orderItem1, orderItem2);
	      when(iOrderItemService.fetchAllOrderItems()).thenReturn(OrderItemList);

	      mockMvc.perform(get("/orders/orderitem/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
