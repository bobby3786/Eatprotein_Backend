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

import com.apputil.dto.AVersionDto;
import com.apputil.entity.AppVersion;
import com.apputil.service.IAppVersionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class AVersionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IAppVersionService iAppVersionService;
	
	    @Test
	    public void testCreateAppVersions() throws Exception {

		  AVersionDto appVersionDto = new AVersionDto();
		  appVersionDto.setAndroidEmployeeVersion("1.0.3");
		  appVersionDto.setAndroidVersion("1.0.2");
		  appVersionDto.setIosEmployeeVersion("2.0.3");
		  appVersionDto.setIosVersion("2.0.4");

	        doNothing().when(iAppVersionService).createAppVersion(appVersionDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/apputil/appversion/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(appVersionDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	 
	  @Test
	  public void testfetchAppVersionDetails() throws Exception {

		  int id = 1;
		  AppVersion appVersion = new AppVersion();
		  
		  appVersion.setId(id);
		  
		  appVersion.setAndroidEmployeeVersion("1.0.3");
		  appVersion.setAndroidVersion("1.0.2");
		  appVersion.setIosEmployeeVersion("2.0.3");

		  appVersion.setIosVersion("2.0.3");

	      when(iAppVersionService.fetchAppVersion(id)).thenReturn(appVersion);

	      mockMvc.perform(get("/apputil/appversion/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.androidEmployeeVersion").value("1.0.3"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.androidVersion").value("1.0.2"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.iosEmployeeVersion").value("2.0.3"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.iosVersion").value("2.0.4"));
	  }
	  
	  @Test
	  public void testupdateAppVersionDetails_Success() throws Exception {
	      
	      AVersionDto appVersionDto = new AVersionDto();
	      appVersionDto.setId(1);
		  appVersionDto.setAndroidEmployeeVersion("1.0.3");
		  appVersionDto.setAndroidVersion("1.0.2");
		  appVersionDto.setIosEmployeeVersion("2.0.3");
		  appVersionDto.setIosVersion("2.0.4");

	      when(iAppVersionService.updateAppVersion(appVersionDto)).thenReturn(true);

	      mockMvc.perform(put("/apputil/appversion/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(appVersionDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testupdateAppVersionDetails_Failure() throws Exception {
	      
		  AVersionDto appVersionDto = new AVersionDto();
	      appVersionDto.setId(1);
		  appVersionDto.setAndroidEmployeeVersion("1.0.3");
		  appVersionDto.setAndroidVersion("1.0.2");
		  appVersionDto.setIosEmployeeVersion("2.0.3");
		  appVersionDto.setIosVersion("2.0.4");

	      when(iAppVersionService.updateAppVersion(appVersionDto)).thenReturn(false);

	      mockMvc.perform(put("/apputil/appversion/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(appVersionDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testdeleteVersionDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iAppVersionService.deleteAppVersion(id)).thenReturn(true);

	      mockMvc.perform(delete("/apputil/appversion/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testdeleteAppVersionDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iAppVersionService.deleteAppVersion(id)).thenReturn(false);

	      mockMvc.perform(delete("/apputil/appversion/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testgetAllApVersions() throws Exception {
		  AppVersion appVersion1 = new AppVersion();
		 

		  appVersion1.setId(1);
		  appVersion1.setAndroidEmployeeVersion("1.0.3");
		  appVersion1.setAndroidVersion("1.0.2");
		  appVersion1.setIosEmployeeVersion("2.0.3");
		  appVersion1.setIosVersion("2.0.4");

		  AppVersion appVersion2 = new AppVersion();
		  appVersion2.setId(2);
		  appVersion2.setAndroidEmployeeVersion("1.0.3");
		  appVersion2.setAndroidVersion("1.0.2");
		  appVersion2.setIosEmployeeVersion("2.0.3");
		  appVersion2.setIosVersion("2.0.4");

		  
		  
	      List<AppVersion> appVersionsList = Arrays.asList(appVersion1, appVersion2);
	      when(iAppVersionService.fetchAllAppVersions()).thenReturn(appVersionsList);

	      mockMvc.perform(get("/apputil/appversion/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }

}
