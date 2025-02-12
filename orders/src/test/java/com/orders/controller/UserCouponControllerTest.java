package com.orders.controller;

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
import com.orders.dto.UserCouponDto;
import com.orders.entity.UserCoupon;
import com.orders.service.IUserCouponService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class UserCouponControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IUserCouponService iUserCouponService;
	
	  @Test
	    public void testCreateUserCoupon() throws Exception {

		  UserCouponDto userCouponDto = new UserCouponDto();
		  userCouponDto.setStatus("active");
		  userCouponDto.setOrderId(1);
		  
	        doNothing().when(iUserCouponService).createUserCoupon(userCouponDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/orders/usercoupon/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userCouponDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchUserCouponDetails() throws Exception {

		  String id = "1";
	      UserCoupon userCoupon = new UserCoupon();
	      userCoupon.setId(id);
	      userCoupon.setStatus("active");
	      userCoupon.setOrderId(1);

	      when(iUserCouponService.fetchUserCoupon(id)).thenReturn(userCoupon);

	      mockMvc.perform(get("/orders/usercoupon/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("active"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(1));
	  }
	  
	  @Test
	  public void testUpdateUserCouponDetails_Success() throws Exception {
	      
	      UserCouponDto userCouponDto = new UserCouponDto();
	      userCouponDto.setId("1");
	      userCouponDto.setStatus("active");
		  userCouponDto.setOrderId(1);

	      when(iUserCouponService.updateUserCoupon(userCouponDto)).thenReturn(true);

	      mockMvc.perform(put("/orders/usercoupon/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userCouponDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateUserCouponDetails_Failure() throws Exception {
	      
		  UserCouponDto userCouponDto = new UserCouponDto();
		  userCouponDto.setId("1");
	      userCouponDto.setStatus("active");
		  userCouponDto.setOrderId(1);
	      
	      when(iUserCouponService.updateUserCoupon(userCouponDto)).thenReturn(false);

	      mockMvc.perform(put("/orders/usercoupon/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(userCouponDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteUserCouponDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iUserCouponService.deleteUserCoupon(id)).thenReturn(true);

	      mockMvc.perform(delete("/orders/usercoupon/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteUserCouponDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iUserCouponService.deleteUserCoupon(id)).thenReturn(false);

	      mockMvc.perform(delete("/orders/usercoupon/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllUserCoupons() throws Exception {
	      UserCoupon userCoupon1 = new UserCoupon();
	      userCoupon1.setId("1");
	      userCoupon1.setStatus("active");
	      userCoupon1.setOrderId(1);

	      UserCoupon userCoupon2 = new UserCoupon();
	      userCoupon2.setId("2");
	      userCoupon2.setStatus("active");
	      userCoupon2.setOrderId(1);

	      List<UserCoupon> userCouponList = Arrays.asList(userCoupon1, userCoupon2);
	      when(iUserCouponService.fetchAllUserCoupons()).thenReturn(userCouponList);

	      mockMvc.perform(get("/orders/usercoupon/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
