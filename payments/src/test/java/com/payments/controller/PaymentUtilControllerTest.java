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
import com.payments.dto.PaymentUtilDto;
import com.payments.entity.PaymentUtil;
import com.payments.service.IPaymentUtilService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentUtilControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IPaymentUtilService iPaymentUtilService;
	
	  @Test
	    public void testCreatePaymentUtil() throws Exception {

		  PaymentUtilDto paymentUtilDto = new PaymentUtilDto();
		  paymentUtilDto.setAmount(500);
		  paymentUtilDto.setStoreId(12);
		  
		  
	        doNothing().when(iPaymentUtilService).createPaymentUtil(paymentUtilDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/payments/paymentutil/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(paymentUtilDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchPaymentUtilDetails() throws Exception {

		  int id = 1;
	      PaymentUtil paymentUtil = new PaymentUtil();
	      paymentUtil.setId(id);
	      paymentUtil.setAmount(500);
	      paymentUtil.setStoreId(12);

	      when(iPaymentUtilService.fetchPaymentUtil(id)).thenReturn(paymentUtil);

	      mockMvc.perform(get("/payments/paymentutil/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.storeId").value(12));
	  }
	  
	  @Test
	  public void testUpdatePaymentUtilDetails_Success() throws Exception {
	      
	      PaymentUtilDto paymentUtilDto = new PaymentUtilDto();
	      paymentUtilDto.setId(1);
	      paymentUtilDto.setAmount(500);
		  paymentUtilDto.setStoreId(12);

	      when(iPaymentUtilService.updatePaymentUtil(paymentUtilDto)).thenReturn(true);

	      mockMvc.perform(put("/payments/paymentutil/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentUtilDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdatePaymentUtilDetails_Failure() throws Exception {
	      
	      PaymentUtilDto paymentUtilDto = new PaymentUtilDto();
	      paymentUtilDto.setId(1);
	      paymentUtilDto.setAmount(500);
		  paymentUtilDto.setStoreId(12);
	      
	      when(iPaymentUtilService.updatePaymentUtil(paymentUtilDto)).thenReturn(false);

	      mockMvc.perform(put("/payments/paymentutil/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(paymentUtilDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeletePaymentUtilDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iPaymentUtilService.deletePaymentUtil(id)).thenReturn(true);

	      mockMvc.perform(delete("/payments/paymentutil/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeletePaymentUtilDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iPaymentUtilService.deletePaymentUtil(id)).thenReturn(false);

	      mockMvc.perform(delete("/payments/paymentutil/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllPaymentUtils() throws Exception {
	      PaymentUtil paymentUtil1 = new PaymentUtil();
	      paymentUtil1.setId(1);
	      paymentUtil1.setAmount(500);
	      paymentUtil1.setStoreId(12);

	      PaymentUtil paymentUtil2 = new PaymentUtil();
	      paymentUtil2.setId(2);
	      paymentUtil2.setAmount(500);
	      paymentUtil2.setStoreId(12);

	      List<PaymentUtil> PaymentUtilList = Arrays.asList(paymentUtil1, paymentUtil2);
	      when(iPaymentUtilService.fetchAllPaymentUtils()).thenReturn(PaymentUtilList);

	      mockMvc.perform(get("/payments/paymentutil/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
