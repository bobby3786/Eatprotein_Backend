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

import com.apputil.dto.BannerDto;
import com.apputil.entity.Banner;
import com.apputil.service.IBannerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class BannerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IBannerService iBannerService;
	
	  @Test
	    public void testCreateBanner() throws Exception {

		  BannerDto bannerDto = new BannerDto();
		  bannerDto.setImagePath("apputil/banner.jpg");
		  bannerDto.setStatus("Active");
		  
	        doNothing().when(iBannerService).createBanner(bannerDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/apputil/banner/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(bannerDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchBannerDetails() throws Exception {

		  int id = 1;
	      Banner banner = new Banner();
	      banner.setId(id);
	      banner.setImagePath("apputil/banner.jpg");
	      banner.setStatus("Active");

	      when(iBannerService.fetchBanner(id)).thenReturn(banner);

	      mockMvc.perform(get("/apputil/banner/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.imagePath").value("apputil/banner.jpg"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Active"));
	  }
	  
	  @Test
	  public void testUpdateBannerDetails_Success() throws Exception {
	      
	      BannerDto bannerDto = new BannerDto();
	      bannerDto.setId(1);
	      bannerDto.setImagePath("apputil/banner.jpg");
	      bannerDto.setStatus("Active");

	      when(iBannerService.updateBanner(bannerDto)).thenReturn(true);

	      mockMvc.perform(put("/apputil/banner/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(bannerDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateBannerDetails_Failure() throws Exception {
	      
	      BannerDto bannerDto = new BannerDto();
	      bannerDto.setId(1);
	      bannerDto.setImagePath("apputil/banner.jpg");
	      bannerDto.setStatus("Active");
	      
	      when(iBannerService.updateBanner(bannerDto)).thenReturn(false);

	      mockMvc.perform(put("/apputil/banner/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(bannerDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteBannerDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iBannerService.deleteBanner(id)).thenReturn(true);

	      mockMvc.perform(delete("/apputil/banner/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteBannerDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iBannerService.deleteBanner(id)).thenReturn(false);

	      mockMvc.perform(delete("/apputil/banner/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllBanners() throws Exception {
	      Banner banner1 = new Banner();
	      banner1.setId(1);
	      banner1.setImagePath("apputil/banner.jpg");
	      banner1.setStatus("Active");

	      Banner banner2 = new Banner();
	      banner2.setId(2);
	      banner2.setImagePath("apputil/banner.jpg");
	      banner2.setStatus("InActive");

	      List<Banner> BannerList = Arrays.asList(banner1, banner2);
	      when(iBannerService.fetchAllBanners()).thenReturn(BannerList);

	      mockMvc.perform(get("/apputil/banner/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
