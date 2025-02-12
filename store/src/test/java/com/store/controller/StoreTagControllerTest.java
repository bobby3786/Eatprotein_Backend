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
import com.store.dto.StoreTagDto;
import com.store.entity.StoreTag;
import com.store.service.IStoreTagService;


@SpringBootTest
@AutoConfigureMockMvc
public class StoreTagControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IStoreTagService iStoreTagService;
	
	  @Test
	    public void testCreateStoreTag() throws Exception {

		  StoreTagDto storeTagDto = new StoreTagDto();
		  storeTagDto.setStoreId(3);
		  storeTagDto.setTagId(2);
		  
	        doNothing().when(iStoreTagService).createStoreTag(storeTagDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/store/storetag/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(storeTagDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchStoreTagDetails() throws Exception {

		  String id = "1";
	      StoreTag storeTag = new StoreTag();
	      storeTag.setId(id);
	      storeTag.setStoreId(3);
	      storeTag.setTagId(2);

	      when(iStoreTagService.fetchStoreTag(id)).thenReturn(storeTag);

	      mockMvc.perform(get("/store/storetag/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.storeId").value(3))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.tagId").value(2));
	  }
	  
	  @Test
	  public void testUpdateStoreTagDetails_Success() throws Exception {
	      
	      StoreTagDto storeTagDto = new StoreTagDto();
	      storeTagDto.setId("1");
	      storeTagDto.setStoreId(3);
		  storeTagDto.setTagId(2);

	      when(iStoreTagService.updateStoreTag(storeTagDto)).thenReturn(true);

	      mockMvc.perform(put("/store/storetag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeTagDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateStoreTagDetails_Failure() throws Exception {
	      
	      StoreTagDto storeTagDto = new StoreTagDto();
	      storeTagDto.setId("1");
	      storeTagDto.setStoreId(3);
		  storeTagDto.setTagId(2);
	      
	      when(iStoreTagService.updateStoreTag(storeTagDto)).thenReturn(false);

	      mockMvc.perform(put("/store/storetag/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(storeTagDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteStoreTagDetails_Success() throws Exception {
	      
	      String id = "1";
	      when(iStoreTagService.deleteStoreTag(id)).thenReturn(true);

	      mockMvc.perform(delete("/store/storetag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteStoreTagDetails_Failure() throws Exception {
	      
	      String id = "1";
	      when(iStoreTagService.deleteStoreTag(id)).thenReturn(false);

	      mockMvc.perform(delete("/store/storetag/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllStoreTags() throws Exception {
	      StoreTag storeTag1 = new StoreTag();
	      storeTag1.setId("1");
	      storeTag1.setStoreId(3);
	      storeTag1.setTagId(2);

	      StoreTag storeTag2 = new StoreTag();
	      storeTag2.setId("2");
	      storeTag2.setStoreId(3);
	      storeTag2.setTagId(2);
		  
		  
	      List<StoreTag> storeTagList = Arrays.asList(storeTag1, storeTag2);
	      when(iStoreTagService.fetchAllStoreTags()).thenReturn(storeTagList);

	      mockMvc.perform(get("/store/storetag/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }

	
}
