package com.apputil.controller;

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

import com.apputil.dto.BannerStoreDto;
import com.apputil.entity.BannerStore;
import com.apputil.service.IBannerStoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class BannerStoreControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IBannerStoreService iBannerStoreService;
	
	  @Test
	    public void testCreateBannerStore() throws Exception {

		  BannerStoreDto bannerStoreDto = new BannerStoreDto();
		  bannerStoreDto.setImagePath("apputil/BannerStore.jpg");
		  bannerStoreDto.setStatus("Active");
		  
	        doNothing().when(iBannerStoreService).createBannerStore(bannerStoreDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/apputil/bannerstore/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(bannerStoreDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchBannerStoreDetails() throws Exception {

		  int id = 1;
	      BannerStore bannerStore = new BannerStore();
	      bannerStore.setId(id);
	      bannerStore.setImagePath("apputil/BannerStore.jpg");
	      bannerStore.setStatus("Active");

	      when(iBannerStoreService.fetchBannerStore(id)).thenReturn(bannerStore);

	      mockMvc.perform(get("/apputil/bannerstore/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.imagePath").value("apputil/BannerStore.jpg"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Active"));
	  }
	  
	  @Test
	  public void testUpdateBannerStoreDetails_Success() throws Exception {
	      
	      BannerStoreDto bannerStoreDto = new BannerStoreDto();
	      bannerStoreDto.setId(1);
	      bannerStoreDto.setImagePath("apputil/BannerStore.jpg");
	      bannerStoreDto.setStatus("Active");

	      when(iBannerStoreService.updateBannerStore(bannerStoreDto)).thenReturn(true);

	      mockMvc.perform(put("/apputil/bannerstore/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(bannerStoreDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateBannerStoreDetails_Failure() throws Exception {
	      
	      BannerStoreDto bannerStoreDto = new BannerStoreDto();
	      bannerStoreDto.setId(1);
	      bannerStoreDto.setImagePath("apputil/bannerstore.jpg");
	      bannerStoreDto.setStatus("Active");
	      
	      when(iBannerStoreService.updateBannerStore(bannerStoreDto)).thenReturn(false);

	      mockMvc.perform(put("/apputil/bannerstore/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(bannerStoreDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteBannerStoreDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iBannerStoreService.deleteBannerStore(id)).thenReturn(true);

	      mockMvc.perform(delete("/apputil/bannerstore/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteBannerStoreDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iBannerStoreService.deleteBannerStore(id)).thenReturn(false);

	      mockMvc.perform(delete("/apputil/bannerstore/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllBannerStores() throws Exception {
	      BannerStore bannerStore1 = new BannerStore();
	      bannerStore1.setId(1);
	      bannerStore1.setImagePath("apputil/BannerStore.jpg");
	      bannerStore1.setStatus("Active");

	      BannerStore bannerStore2 = new BannerStore();
	      bannerStore2.setId(2);
	      bannerStore2.setImagePath("apputil/BannerStore.jpg");
	      bannerStore2.setStatus("InActive");

	      List<BannerStore> bannerStoreList = Arrays.asList(bannerStore1, bannerStore2);
	      when(iBannerStoreService.fetchAllBannerStores()).thenReturn(bannerStoreList);

	      mockMvc.perform(get("/apputil/bannerstore/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}

