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
import com.payments.dto.PaymentDbDto;
import com.payments.entity.PaymentDb;
import com.payments.service.IPaymentDbService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentDbControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IPaymentDbService iPaymentDbService;
	
	  @Test
	    public void testCreatePaymentDb() throws Exception {

		  PaymentDbDto paymentDbDto = new PaymentDbDto();
		  paymentDbDto.setDeliveryCharges(32.50);
		  paymentDbDto.setOrdersCount(34);
		  
		  
	        doNothing().when(iPaymentDbService).createPaymentDb(paymentDbDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/payments/paymentdb/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(paymentDbDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchPaymentDbDetails() throws Exception {

		  int id = 1;
	      PaymentDb paymentDb = new PaymentDb();
	      paymentDb.setId(id);
	      paymentDb.setDeliveryCharges(32.50);
	      paymentDb.setOrdersCount(34);

	      when(iPaymentDbService.fetchPaymentDb(id)).thenReturn(paymentDb);

	      mockMvc.perform(get("/payments/paymentdb/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryCharges").value(32.50))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.ordersCount").value(34));
	  }
	  
	  @Test
	  public void testUpdatePaymentDbDetails_Success() throws Exception {
	      
	      PaymentDbDto paymentDbDto = new PaymentDbDto();
	      paymentDbDto.setId(1);
	      paymentDbDto.setDeliveryCharges(32.50);
		  paymentDbDto.setOrdersCount(34);

	      when(iPaymentDbService.updatePaymentDb(paymentDbDto)).thenReturn(true);

	      mockMvc.perform(put("/payments/paymentdb/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentDbDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdatePaymentDbDetails_Failure() throws Exception {
	      
	      PaymentDbDto paymentDbDto = new PaymentDbDto();
	      paymentDbDto.setId(1);
	      paymentDbDto.setDeliveryCharges(32.50);
		  paymentDbDto.setOrdersCount(34);
	      
	      when(iPaymentDbService.updatePaymentDb(paymentDbDto)).thenReturn(false);

	      mockMvc.perform(put("/payments/paymentdb/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentDbDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeletePaymentDbDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iPaymentDbService.deletePaymentDb(id)).thenReturn(true);

	      mockMvc.perform(delete("/payments/paymentdb/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeletePaymentDbDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iPaymentDbService.deletePaymentDb(id)).thenReturn(false);

	      mockMvc.perform(delete("/payments/paymentdb/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllPaymentDbs() throws Exception {
	      PaymentDb paymentDb1 = new PaymentDb();
	      paymentDb1.setId(1);
	      paymentDb1.setDeliveryCharges(32.50);
	      paymentDb1.setOrdersCount(34);

	      PaymentDb paymentDb2 = new PaymentDb();
	      paymentDb2.setId(2);
	      paymentDb2.setDeliveryCharges(32.50);
	      paymentDb2.setOrdersCount(34);

	      List<PaymentDb> PaymentDbList = Arrays.asList(paymentDb1, paymentDb2);
	      when(iPaymentDbService.fetchAllPaymentDbs()).thenReturn(PaymentDbList);

	      mockMvc.perform(get("/payments/paymentdb/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
