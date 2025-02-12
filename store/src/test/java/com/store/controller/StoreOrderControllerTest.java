package com.store.controller;

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
import com.store.dto.StoreOrderDto;
import com.store.entity.StoreOrder;
import com.store.service.IStoreOrderService;


@SpringBootTest
@AutoConfigureMockMvc
public class StoreOrderControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IStoreOrderService iStoreOrderService;
	
	  @Test
	    public void testCreateStoreOrder() throws Exception {

		  StoreOrderDto storeOrderDto = new StoreOrderDto();
		  storeOrderDto.setTotalPrice(5000);
		  storeOrderDto.setStoreId(1);
		  
	        doNothing().when(iStoreOrderService).createStoreOrder(storeOrderDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/store/storeorder/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(storeOrderDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchStoreOrderDetails() throws Exception {

		  String id = "1";
	      StoreOrder storeOrder = new StoreOrder();
	      storeOrder.setId(id);
	      storeOrder.setTotalPrice(5000);
	      storeOrder.setStoreId(1);

	      when(iStoreOrderService.fetchStoreOrder(id)).thenReturn(storeOrder);

	      mockMvc.perform(get("/store/storeorder/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(5000))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.storeId").value(1));
	  }
	  
	  @Test
	  public void testUpdateStoreOrderDetails_Success() throws Exception {
	      
	      StoreOrderDto storeOrderDto = new StoreOrderDto();
	      storeOrderDto.setId("1");
	      storeOrderDto.setTotalPrice(5000);
		  storeOrderDto.setStoreId(1);

	      when(iStoreOrderService.updateStoreOrder(storeOrderDto)).thenReturn(true);

	      mockMvc.perform(put("/store/storeorder/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeOrderDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateStoreOrderDetails_Failure() throws Exception {
	      
	      StoreOrderDto storeOrderDto = new StoreOrderDto();
	      storeOrderDto.setId("1");
	      storeOrderDto.setTotalPrice(5000);
		  storeOrderDto.setStoreId(1);
	      
	      when(iStoreOrderService.updateStoreOrder(storeOrderDto)).thenReturn(false);

	      mockMvc.perform(put("/store/storeorder/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeOrderDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteStoreOrderDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iStoreOrderService.deleteStoreOrder(id)).thenReturn(true);

	      mockMvc.perform(delete("/store/storeorder/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteStoreOrderDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iStoreOrderService.deleteStoreOrder(id)).thenReturn(false);

	      mockMvc.perform(delete("/store/storeorder/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllStoreOrders() throws Exception {
	      StoreOrder storeOrder1 = new StoreOrder();
	      storeOrder1.setId("1");
	      storeOrder1.setTotalPrice(5000);
	      storeOrder1.setStoreId(1);

	      StoreOrder storeOrder2 = new StoreOrder();
	      storeOrder2.setId("2");
	      storeOrder2.setTotalPrice(5000);
	      storeOrder2.setStoreId(1);

	      List<StoreOrder> storeOrderList = Arrays.asList(storeOrder1, storeOrder2);
	      when(iStoreOrderService.fetchAllStoreOrders()).thenReturn(storeOrderList);

	      mockMvc.perform(get("/store/storeorder/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
