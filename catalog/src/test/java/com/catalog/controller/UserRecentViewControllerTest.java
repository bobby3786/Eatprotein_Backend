package com.catalog.controller;

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

import com.catalog.dto.UserRecentViewDto;
import com.catalog.entity.UserRecentView;
import com.catalog.service.IUserRecentViewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class UserRecentViewControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserRecentViewService iUserRecentViewService;
	
	  @Test
	    public void testCreateUserRecentView() throws Exception {

		  UserRecentViewDto userRecentViewDto = new UserRecentViewDto();
		  userRecentViewDto.setStoreId(10);
		  
	        doNothing().when(iUserRecentViewService).createUserRecentView(userRecentViewDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/catalog/userRecentView/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userRecentViewDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserRecentViewDetails() throws Exception {

		  int id = 1;
	      UserRecentView userRecentView = new UserRecentView();
	      userRecentView.setId(id);
	      userRecentView.setStoreId(10);

	      when(iUserRecentViewService.fetchUserRecentView(id)).thenReturn(userRecentView);

	      mockMvc.perform(get("/catalog/userRecentView/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.storeId").value(10)) ;
	      
	  }
	  
	  @Test
	  public void testUpdateUserRecentViewDetails_Success() throws Exception {
	      
	      UserRecentViewDto userRecentViewDto = new UserRecentViewDto();
	      userRecentViewDto.setId(1);
	      userRecentViewDto.setStoreId(10);

	      when(iUserRecentViewService.updateUserRecentView(userRecentViewDto)).thenReturn(true);

	      mockMvc.perform(put("/catalog/userRecentView/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userRecentViewDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserRecentViewDetails_Failure() throws Exception {
	      
	      UserRecentViewDto userRecentViewDto = new UserRecentViewDto();
	      userRecentViewDto.setId(1);
	      userRecentViewDto.setStoreId(10);
	      
	      when(iUserRecentViewService.updateUserRecentView(userRecentViewDto)).thenReturn(false);

	      mockMvc.perform(put("/catalog/userRecentView/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userRecentViewDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserRecentViewDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iUserRecentViewService.deleteUserRecentView(id)).thenReturn(true);

	      mockMvc.perform(delete("/catalog/userRecentView/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserRecentViewDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iUserRecentViewService.deleteUserRecentView(id)).thenReturn(false);

	      mockMvc.perform(delete("/catalog/userRecentView/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserRecentViews() throws Exception {
	      UserRecentView userRecentView1 = new UserRecentView();
	      userRecentView1.setId(1);
	      userRecentView1.setStoreId(10);

	      UserRecentView userRecentView2 = new UserRecentView();
	      userRecentView2.setId(2);
	      userRecentView2.setStoreId(20);

	      List<UserRecentView> UserRecentViewList = Arrays.asList(userRecentView1, userRecentView2);
	      when(iUserRecentViewService.fetchAllUserRecentViews()).thenReturn(UserRecentViewList);

	      mockMvc.perform(get("/catalog/userRecentView/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
