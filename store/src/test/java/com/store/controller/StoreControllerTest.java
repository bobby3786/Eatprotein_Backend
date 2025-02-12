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
import com.store.dto.StoreDto;
import com.store.entity.Store;
import com.store.service.IStoreService;


@SpringBootTest
@AutoConfigureMockMvc
public class StoreControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IStoreService iStoreService;
	
	  @Test
	    public void testCreateStore() throws Exception {

		  StoreDto storeDto = new StoreDto();
		  storeDto.setStatus("active");
		  storeDto.setCategoryId(1);
		  
	        doNothing().when(iStoreService).createStore(storeDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/store/store/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(storeDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchStoreDetails() throws Exception {

		  String id = "1";
	      Store store = new Store();
	      store.setId(id);
	      store.setStatus("active");
	      store.setCategoryId(1);

	      when(iStoreService.fetchStore(id)).thenReturn(store);

	      mockMvc.perform(get("/store/store/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("active"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").value(1));
	  }
	  
	  @Test
	  public void testUpdateStoreDetails_Success() throws Exception {
	      
	      StoreDto storeDto = new StoreDto();
	      storeDto.setId("1");
	      storeDto.setStatus("active");
	      storeDto.setCategoryId(1);

	      when(iStoreService.updateStore(storeDto)).thenReturn(true);

	      mockMvc.perform(put("/store/store/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateStoreDetails_Failure() throws Exception {
	      
	      StoreDto storeDto = new StoreDto();
	      storeDto.setId("1");
	      storeDto.setStatus("active");
	      storeDto.setCategoryId(1);
	      
	      when(iStoreService.updateStore(storeDto)).thenReturn(false);

	      mockMvc.perform(put("/store/store/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteStoreDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iStoreService.deleteStore(id)).thenReturn(true);

	      mockMvc.perform(delete("/store/store/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteStoreDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iStoreService.deleteStore(id)).thenReturn(false);

	      mockMvc.perform(delete("/store/store/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllStores() throws Exception {
	      Store store1 = new Store();
	      store1.setId("1");
	      store1.setStatus("active");
	      store1.setCategoryId(1);

	      Store store2 = new Store();
	      store2.setId("2");
	      store2.setStatus("active");
	      store2.setCategoryId(1);

	      List<Store> StoreList = Arrays.asList(store1, store2);
	      when(iStoreService.fetchAllStores()).thenReturn(StoreList);

	      mockMvc.perform(get("/store/store/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }



}
