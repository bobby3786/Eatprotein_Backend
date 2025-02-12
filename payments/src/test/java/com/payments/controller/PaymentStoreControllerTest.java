package com.payments.controller;

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
import com.payments.dto.PaymentStoreDto;
import com.payments.entity.PaymentStore;
import com.payments.service.IPaymentStoreService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentStoreControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IPaymentStoreService iPaymentStoreService;
	
	  @Test
	    public void testCreatePaymentStore() throws Exception {

		  PaymentStoreDto paymentStoreDto = new PaymentStoreDto();
		  paymentStoreDto.setDeliveryCharges(32.50);
		  paymentStoreDto.setPaymentStatus("completed");
		  
		  
	        doNothing().when(iPaymentStoreService).createPaymentStore(paymentStoreDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/payments/paymentstore/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(paymentStoreDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchPaymentStoreDetails() throws Exception {

		  int id = 1;
	      PaymentStore paymentStore = new PaymentStore();
	      paymentStore.setId(id);
	      paymentStore.setDeliveryCharges(32.50);
	      paymentStore.setPaymentStatus("completed");

	      when(iPaymentStoreService.fetchPaymentStore(id)).thenReturn(paymentStore);

	      mockMvc.perform(get("/payments/paymentstore/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryCharges").value(32.50))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.paymentStatus").value("completed"));
	  }
	  
	  @Test
	  public void testUpdatePaymentStoreDetails_Success() throws Exception {
	      
	      PaymentStoreDto paymentStoreDto = new PaymentStoreDto();
	      paymentStoreDto.setId(1);
	      paymentStoreDto.setDeliveryCharges(32.50);
	      paymentStoreDto.setPaymentStatus("completed");

	      when(iPaymentStoreService.updatePaymentStore(paymentStoreDto)).thenReturn(true);

	      mockMvc.perform(put("/payments/paymentstore/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentStoreDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdatePaymentStoreDetails_Failure() throws Exception {
	      
	      PaymentStoreDto paymentStoreDto = new PaymentStoreDto();
	      paymentStoreDto.setId(1);
	      paymentStoreDto.setDeliveryCharges(32.50);
	      paymentStoreDto.setPaymentStatus("completed");
	      
	      when(iPaymentStoreService.updatePaymentStore(paymentStoreDto)).thenReturn(false);

	      mockMvc.perform(put("/payments/paymentstore/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentStoreDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeletePaymentStoreDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iPaymentStoreService.deletePaymentStore(id)).thenReturn(true);

	      mockMvc.perform(delete("/payments/paymentstore/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeletePaymentStoreDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iPaymentStoreService.deletePaymentStore(id)).thenReturn(false);

	      mockMvc.perform(delete("/payments/paymentstore/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllPaymentStores() throws Exception {
	      PaymentStore paymentStore1 = new PaymentStore();
	      paymentStore1.setId(1);
	      paymentStore1.setDeliveryCharges(32.50);
	      paymentStore1.setPaymentStatus("completed");

	      PaymentStore paymentStore2 = new PaymentStore();
	      paymentStore2.setId(2);
	      paymentStore2.setDeliveryCharges(32.50);
	      paymentStore2.setPaymentStatus("completed");

	      List<PaymentStore> PaymentStoreList = Arrays.asList(paymentStore1, paymentStore2);
	      when(iPaymentStoreService.fetchAllPaymentStores()).thenReturn(PaymentStoreList);

	      mockMvc.perform(get("/payments/paymentstore/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
