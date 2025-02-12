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

import com.apputil.dto.SettingsDto;
import com.apputil.entity.Settings;
import com.apputil.service.ISettingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
public class SettingsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private ISettingsService iSettingsService;
	
	  @Test
	    public void testCreateSettings() throws Exception {

		  SettingsDto settingsDto = new SettingsDto();
	        settingsDto.setPackageCharge(1000.08);
	        settingsDto.setPlatformFee(500.05);
	        settingsDto.setUtility(300);

	        doNothing().when(iSettingsService).createSettings(settingsDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/apputil/settings/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(settingsDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchSettingsDetails() throws Exception {

		  int id = 1;
	      Settings settings = new Settings();
	      settings.setId(id);
	      settings.setPlatformFee(500.05);
	      settings.setPackageCharge(1000.08);
	      settings.setUtility(300.0);

	      when(iSettingsService.fetchSettings(id)).thenReturn(settings);

	      mockMvc.perform(get("/apputil/settings/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.platformFee").value(500.05))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.packageCharge").value(1000.08))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.utility").value(300.0));
	  }
	  
	  @Test
	  public void testUpdateSettingsDetails_Success() throws Exception {
	      
	      SettingsDto settingsDto = new SettingsDto();
	      settingsDto.setId(1);
	      settingsDto.setPlatformFee(600.05);
	      settingsDto.setPackageCharge(1100.08);
	      settingsDto.setUtility(350.0);

	      when(iSettingsService.updateSettings(settingsDto)).thenReturn(true);

	      mockMvc.perform(put("/apputil/settings/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(settingsDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateSettingsDetails_Failure() throws Exception {
	      
	      SettingsDto settingsDto = new SettingsDto();
	      settingsDto.setId(1);
	      settingsDto.setPlatformFee(600.05);
	      settingsDto.setPackageCharge(1100.08);
	      settingsDto.setUtility(350.0);

	      when(iSettingsService.updateSettings(settingsDto)).thenReturn(false);

	      mockMvc.perform(put("/apputil/settings/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(settingsDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteSettingsDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iSettingsService.deleteSettings(id)).thenReturn(true);

	      mockMvc.perform(delete("/apputil/settings/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteSettingsDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iSettingsService.deleteSettings(id)).thenReturn(false);

	      mockMvc.perform(delete("/apputil/settings/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllSettings() throws Exception {
	      Settings settings1 = new Settings();
	      settings1.setId(1);
	      settings1.setPlatformFee(500.05);
	      settings1.setPackageCharge(1000.08);
	      settings1.setUtility(300.0);

	      Settings settings2 = new Settings();
	      settings2.setId(2);
	      settings2.setPlatformFee(600.05);
	      settings2.setPackageCharge(1200.08);
	      settings2.setUtility(350.0);

	      List<Settings> settingsList = Arrays.asList(settings1, settings2);
	      when(iSettingsService.fetchAllSettings()).thenReturn(settingsList);

	      mockMvc.perform(get("/apputil/settings/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }







  
}

