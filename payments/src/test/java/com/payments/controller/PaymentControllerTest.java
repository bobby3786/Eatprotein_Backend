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
import com.payments.dto.PaymentDto;
import com.payments.entity.Payment;
import com.payments.service.IPaymentService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IPaymentService iPaymentService;
	
	  @Test
	    public void testCreatePayment() throws Exception {

		  PaymentDto paymentDto = new PaymentDto();
		  paymentDto.setDeliveryCharges(32.50);
		  paymentDto.setAppPayment(50);
		  
	        doNothing().when(iPaymentService).createPayment(paymentDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/payments/payment/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(paymentDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchPaymentDetails() throws Exception {

		  int id = 1;
	      Payment payment = new Payment();
	      payment.setId(id);
	      payment.setDeliveryCharges(32.50);
	      payment.setAppPayment(50);

	      when(iPaymentService.fetchPayment(id)).thenReturn(payment);

	      mockMvc.perform(get("/payments/payment/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryCharges").value(32.50))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.appPayment").value(50));
	  }
	  
	  @Test
	  public void testUpdatePaymentDetails_Success() throws Exception {
	      
	      PaymentDto paymentDto = new PaymentDto();
	      paymentDto.setId(1);
	      paymentDto.setDeliveryCharges(32.50);
		  paymentDto.setAppPayment(50);

	      when(iPaymentService.updatePayment(paymentDto)).thenReturn(true);

	      mockMvc.perform(put("/payments/payment/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdatePaymentDetails_Failure() throws Exception {
	      
	      PaymentDto paymentDto = new PaymentDto();
	      paymentDto.setId(1);
	      paymentDto.setDeliveryCharges(32.50);
		  paymentDto.setAppPayment(50);
	      
	      when(iPaymentService.updatePayment(paymentDto)).thenReturn(false);

	      mockMvc.perform(put("/payments/payment/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeletePaymentDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iPaymentService.deletePayment(id)).thenReturn(true);

	      mockMvc.perform(delete("/payments/payment/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeletePaymentDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iPaymentService.deletePayment(id)).thenReturn(false);

	      mockMvc.perform(delete("/payments/payment/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllPayments() throws Exception {
	      Payment payment1 = new Payment();
	      payment1.setId(1);
	      payment1.setDeliveryCharges(32.50);
	      payment1.setAppPayment(50);

	      Payment payment2 = new Payment();
	      payment2.setId(2);
	      payment2.setDeliveryCharges(32.50);
	      payment2.setAppPayment(50);

	      List<Payment> PaymentList = Arrays.asList(payment1, payment2);
	      when(iPaymentService.fetchAllPayments()).thenReturn(PaymentList);

	      mockMvc.perform(get("/payments/payment/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
