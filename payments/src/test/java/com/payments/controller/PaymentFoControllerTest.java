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
import com.payments.dto.PaymentFoDto;
import com.payments.entity.PaymentFo;
import com.payments.service.IPaymentFoService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentFoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IPaymentFoService iPaymentFoService;
	
	  @Test
	    public void testCreatePaymentFo() throws Exception {

		  PaymentFoDto paymentFoDto = new PaymentFoDto();
		  paymentFoDto.setDeliveryCharges(32.50);
		  paymentFoDto.setOrderAmount(500);
		  
		  
	        doNothing().when(iPaymentFoService).createPaymentFo(paymentFoDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/payments/paymentfo/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(paymentFoDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchPaymentFoDetails() throws Exception {

		  int id = 1;
	      PaymentFo paymentFo = new PaymentFo();
	      paymentFo.setId(id);
	      paymentFo.setDeliveryCharges(32.50);
	      paymentFo.setOrderAmount(500);

	      when(iPaymentFoService.fetchPaymentFo(id)).thenReturn(paymentFo);

	      mockMvc.perform(get("/payments/paymentfo/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryCharges").value(32.50))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.orderAmount").value(500));
	  }
	  
	  @Test
	  public void testUpdatePaymentFoDetails_Success() throws Exception {
	      
	      PaymentFoDto paymentFoDto = new PaymentFoDto();
	      paymentFoDto.setId(1);
	      paymentFoDto.setDeliveryCharges(32.50);
		  paymentFoDto.setOrderAmount(500);

	      when(iPaymentFoService.updatePaymentFo(paymentFoDto)).thenReturn(true);

	      mockMvc.perform(put("/payments/paymentfo/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentFoDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdatePaymentFoDetails_Failure() throws Exception {
	      
	      PaymentFoDto paymentFoDto = new PaymentFoDto();
	      paymentFoDto.setId(1);
	      paymentFoDto.setDeliveryCharges(32.50);
		  paymentFoDto.setOrderAmount(500);
	      
	      when(iPaymentFoService.updatePaymentFo(paymentFoDto)).thenReturn(false);

	      mockMvc.perform(put("/payments/paymentfo/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentFoDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeletePaymentFoDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iPaymentFoService.deletePaymentFo(id)).thenReturn(true);

	      mockMvc.perform(delete("/payments/paymentfo/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeletePaymentFoDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iPaymentFoService.deletePaymentFo(id)).thenReturn(false);

	      mockMvc.perform(delete("/payments/paymentfo/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllPaymentFos() throws Exception {
	      PaymentFo paymentFo1 = new PaymentFo();
	      paymentFo1.setId(1);
	      paymentFo1.setDeliveryCharges(32.50);
	      paymentFo1.setOrderAmount(500);

	      PaymentFo paymentFo2 = new PaymentFo();
	      paymentFo2.setId(2);
	      paymentFo2.setDeliveryCharges(32.50);
	      paymentFo2.setOrderAmount(500);

	      List<PaymentFo> PaymentFoList = Arrays.asList(paymentFo1, paymentFo2);
	      when(iPaymentFoService.fetchAllPaymentFos()).thenReturn(PaymentFoList);

	      mockMvc.perform(get("/payments/paymentfo/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }

}
