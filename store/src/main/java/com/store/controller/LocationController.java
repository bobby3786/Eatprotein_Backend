package com.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;
import com.store.dto.LocationDto;
import com.store.entity.Location;
import com.store.service.ILocationService;

@RestController
@RequestMapping("store/location")
public class LocationController {
	
	@Autowired
	private ILocationService iLocationService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createLocation(@RequestBody LocationDto locationDto){
		iLocationService.createLocation(locationDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Location> fetchLocationDetails(@RequestParam("id") String id){
		
		Location location = iLocationService.fetchLocation(id);
	return ResponseEntity.status(HttpStatus.OK).body(location);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateLocationDetails(@RequestBody LocationDto locationDto){
		
		boolean isUpdated = iLocationService.updateLocation(locationDto);
		if(isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
			
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500, Constants.MESSAGE_500));
			
		}
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLocationDetails(@RequestParam("id") String id){
		
		boolean isDeleted = iLocationService.deleteLocation(id);
		if(isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
			
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500, Constants.MESSAGE_500));
			
		}
		
	}

	@GetMapping("/All")
	public ApiResponse<List<Location>> getAllLocations(){
	List<Location> location =	iLocationService.fetchAllLocations();
	
	return new ApiResponse<List<Location>>("200", "success", location);
	}



}
