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
import com.orders.dto.OrderRatingDto;
import com.orders.entity.OrderRating;
import com.orders.service.IOrderRatingService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderRatingControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IOrderRatingService iOrderRatingService;
	
	  @Test
	    public void testCreateOrderRating() throws Exception {

		  OrderRatingDto orderRatingDto = new OrderRatingDto();
		  orderRatingDto.setStoreId(10);
		  orderRatingDto.setOrderId(1);
		  
	        doNothing().when(iOrderRatingService).createOrderRating(orderRatingDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/orders/orderrating/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(orderRatingDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchOrderRatingDetails() throws Exception {

		  String id = "1";
	      OrderRating orderRating = new OrderRating();
	      orderRating.setId(id);
	      orderRating.setStoreId(10);
	      orderRating.setOrderId(1);

	      when(iOrderRatingService.fetchOrderRating(id)).thenReturn(orderRating);

	      mockMvc.perform(get("/orders/orderrating/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.storeId").value(10))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(1));
	  }
	  
	  @Test
	  public void testUpdateOrderRatingDetails_Success() throws Exception {
	      
	      OrderRatingDto orderRatingDto = new OrderRatingDto();
	      orderRatingDto.setId("1");
	      orderRatingDto.setStoreId(10);
		  orderRatingDto.setOrderId(1);

	      when(iOrderRatingService.updateOrderRating(orderRatingDto)).thenReturn(true);

	      mockMvc.perform(put("/orders/orderrating/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderRatingDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateOrderRatingDetails_Failure() throws Exception {
	      
	      OrderRatingDto orderRatingDto = new OrderRatingDto();
	      orderRatingDto.setId("1");
	      orderRatingDto.setStoreId(10);
		  orderRatingDto.setOrderId(1);
	      
	      when(iOrderRatingService.updateOrderRating(orderRatingDto)).thenReturn(false);

	      mockMvc.perform(put("/orders/orderrating/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(orderRatingDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteOrderRatingDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iOrderRatingService.deleteOrderRating(id)).thenReturn(true);

	      mockMvc.perform(delete("/orders/orderrating/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteOrderRatingDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iOrderRatingService.deleteOrderRating(id)).thenReturn(false);

	      mockMvc.perform(delete("/orders/orderrating/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllOrderRatings() throws Exception {
	      OrderRating orderRating1 = new OrderRating();
	      orderRating1.setId("1");
	      orderRating1.setStoreId(10);
	      orderRating1.setOrderId(1);

	      OrderRating orderRating2 = new OrderRating();
	      orderRating2.setId("2");
	      orderRating2.setStoreId(10);
	      orderRating2.setOrderId(1);

	      List<OrderRating> OrderRatingList = Arrays.asList(orderRating1, orderRating2);
	      when(iOrderRatingService.fetchAllOrderRatings()).thenReturn(OrderRatingList);

	      mockMvc.perform(get("/orders/orderrating/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
