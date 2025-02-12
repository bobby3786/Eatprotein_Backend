package com.authentication.controller;

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

import com.authentication.dto.WalletTransactionDto;
import com.authentication.entity.WalletTransaction;
import com.authentication.service.IWalletTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class WalletTransactionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IWalletTransactionService iWalletTransactionService;
	
	  @Test
	    public void testCreateWalletTransaction() throws Exception {

		  WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
		  walletTransactionDto.setAmount("1000");
		  walletTransactionDto.setReason("Bad Quality");
		  
	        doNothing().when(iWalletTransactionService).createWalletTransaction(walletTransactionDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/wallettransaction/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(walletTransactionDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchWalletTransactionDetails() throws Exception {

		  int id = 1;
	      WalletTransaction walletTransaction = new WalletTransaction();
	      walletTransaction.setId(id);
	      walletTransaction.setAmount("1000");
	      walletTransaction.setReason("Bad Quality");

	      when(iWalletTransactionService.fetchWalletTransaction(id)).thenReturn(walletTransaction);

	      mockMvc.perform(get("/auth/wallettransaction/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("1000"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.reason").value("Bad Quality"));
	  }
	  
	  @Test
	  public void testUpdateWalletTransactionDetails_Success() throws Exception {
	      
	      WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
	      walletTransactionDto.setId(1);
	      walletTransactionDto.setAmount("1000");
		  walletTransactionDto.setReason("Bad Quality");

	      when(iWalletTransactionService.updateWalletTransaction(walletTransactionDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/wallettransaction/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(walletTransactionDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateWalletTransactionDetails_Failure() throws Exception {
	      
	      WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
	      walletTransactionDto.setId(1);
	      walletTransactionDto.setAmount("1000");
		  walletTransactionDto.setReason("Bad Quality");
	      
	      when(iWalletTransactionService.updateWalletTransaction(walletTransactionDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/wallettransaction/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(walletTransactionDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteWalletTransactionDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iWalletTransactionService.deleteWalletTransaction(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/wallettransaction/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteWalletTransactionDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iWalletTransactionService.deleteWalletTransaction(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/wallettransaction/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllWalletTransactions() throws Exception {
	      WalletTransaction walletTransaction1 = new WalletTransaction();
	      walletTransaction1.setId(1);
	      walletTransaction1.setAmount("1000");
	      walletTransaction1.setReason("Bad Quality");

	      WalletTransaction walletTransaction2 = new WalletTransaction();
	      walletTransaction2.setId(2);
	      walletTransaction2.setAmount("1000");
	      walletTransaction2.setReason("Bad Quality");

	      List<WalletTransaction> WalletTransactionList = Arrays.asList(walletTransaction1, walletTransaction2);
	      when(iWalletTransactionService.fetchAllWalletTransactions()).thenReturn(WalletTransactionList);

	      mockMvc.perform(get("/auth/wallettransaction/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
