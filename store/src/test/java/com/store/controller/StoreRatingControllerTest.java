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
import com.store.dto.StoreRatingDto;
import com.store.entity.StoreRating;
import com.store.service.IStoreRatingService;


@SpringBootTest
@AutoConfigureMockMvc
public class StoreRatingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IStoreRatingService iStoreRatingService;
	
	  @Test
	    public void testCreateStoreRating() throws Exception {

		  StoreRatingDto storeRatingDto = new StoreRatingDto();
		  storeRatingDto.setComments("good");
		  storeRatingDto.setRating(5);
		  
	        doNothing().when(iStoreRatingService).createStoreRating(storeRatingDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/store/storerating/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(storeRatingDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchStoreRatingDetails() throws Exception {

		  String id = "1";
	      StoreRating storeRating = new StoreRating();
	      storeRating.setId(id);
	      storeRating.setComments("good");
	      storeRating.setRating(5);

	      when(iStoreRatingService.fetchStoreRating(id)).thenReturn(storeRating);

	      mockMvc.perform(get("/store/storerating/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.comments").value("good"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(5));
	  }
	  
	  @Test
	  public void testUpdateStoreRatingDetails_Success() throws Exception {
	      
	      StoreRatingDto storeRatingDto = new StoreRatingDto();
	      storeRatingDto.setId("1");
	      storeRatingDto.setComments("good");
		  storeRatingDto.setRating(5);

	      when(iStoreRatingService.updateStoreRating(storeRatingDto)).thenReturn(true);

	      mockMvc.perform(put("/store/storerating/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeRatingDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateStoreRatingDetails_Failure() throws Exception {
	      
	      StoreRatingDto storeRatingDto = new StoreRatingDto();
	      storeRatingDto.setId("1");
	      storeRatingDto.setComments("good");
		  storeRatingDto.setRating(5);
	      
	      when(iStoreRatingService.updateStoreRating(storeRatingDto)).thenReturn(false);

	      mockMvc.perform(put("/store/storerating/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeRatingDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteStoreRatingDetails_Success() throws Exception {
	      
	      String id = "1";
	      when(iStoreRatingService.deleteStoreRating(id)).thenReturn(true);

	      mockMvc.perform(delete("/store/storerating/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteStoreRatingDetails_Failure() throws Exception {
	      
	      String id = "1";
	      when(iStoreRatingService.deleteStoreRating(id)).thenReturn(false);

	      mockMvc.perform(delete("/store/storerating/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllStoreRatings() throws Exception {
	      StoreRating storeRating1 = new StoreRating();
	      storeRating1.setId("1");
	      storeRating1.setComments("good");
	      storeRating1.setRating(5);

	      StoreRating storeRating2 = new StoreRating();
	      storeRating2.setId("2");
	      storeRating2.setComments("good");
	      storeRating2.setRating(5);
		  
		  
	      List<StoreRating> StoreRatingList = Arrays.asList(storeRating1, storeRating2);
	      when(iStoreRatingService.fetchAllStoreRatings()).thenReturn(StoreRatingList);

	      mockMvc.perform(get("/store/storerating/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }

	
}
