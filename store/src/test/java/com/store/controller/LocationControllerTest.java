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
import com.store.dto.LocationDto;
import com.store.entity.Location;
import com.store.service.ILocationService;


@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private ILocationService iLocationService;
	
	  @Test
	    public void testCreateLocation() throws Exception {

		  LocationDto locationDto = new LocationDto();
		  locationDto.setCity("kavali");
		  locationDto.setState("AP");
		  
	        doNothing().when(iLocationService).createLocation(locationDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/store/location/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(locationDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchLocationDetails() throws Exception {

		  String id = "1";
	      Location location = new Location();
	      location.setId(id);
	      location.setCity("kavali");
	      location.setState("AP");

	      when(iLocationService.fetchLocation(id)).thenReturn(location);

	      mockMvc.perform(get("/store/location/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("kavali"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("AP"));
	  }
	  
	  @Test
	  public void testUpdateLocationDetails_Success() throws Exception {
	      
	      LocationDto locationDto = new LocationDto();
	      locationDto.setId("1");
	      locationDto.setCity("kavali");
		  locationDto.setState("AP");

	      when(iLocationService.updateLocation(locationDto)).thenReturn(true);

	      mockMvc.perform(put("/store/location/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(locationDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateLocationDetails_Failure() throws Exception {
	      
	      LocationDto locationDto = new LocationDto();
	      locationDto.setId("1");
	      locationDto.setCity("kavali");
		  locationDto.setState("AP");
	      
	      when(iLocationService.updateLocation(locationDto)).thenReturn(false);

	      mockMvc.perform(put("/store/location/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(locationDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteLocationDetails_Success() throws Exception {
	      
		  String id = "1";
	      when(iLocationService.deleteLocation(id)).thenReturn(true);

	      mockMvc.perform(delete("/store/location/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteLocationDetails_Failure() throws Exception {
	      
		  String id = "1";
	      when(iLocationService.deleteLocation(id)).thenReturn(false);

	      mockMvc.perform(delete("/store/location/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllLocations() throws Exception {
	      Location location1 = new Location();
	      location1.setId("1");
	      location1.setCity("kavali");
	      location1.setState("AP");

	      Location location2 = new Location();
	      location2.setId("2");
	      location2.setCity("kavali");
	      location2.setState("AP");

	      List<Location> locationList = Arrays.asList(location1, location2);
	      when(iLocationService.fetchAllLocations()).thenReturn(locationList);

	      mockMvc.perform(get("/store/location/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


}
