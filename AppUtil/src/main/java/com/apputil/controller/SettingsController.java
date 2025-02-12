package com.apputil.controller;

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

import com.apputil.dto.SettingsDto;
import com.apputil.entity.Settings;
import com.apputil.service.ISettingsService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("apputil/settings")
public class SettingsController {
	
	@Autowired
	private ISettingsService iSettingsService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createSettings(@RequestBody SettingsDto settingsDto){
		iSettingsService.createSettings(settingsDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Settings> fetchSettingsDetails(@RequestParam int id){
		Settings settings=iSettingsService.fetchSettings(id);
		return ResponseEntity.status(HttpStatus.OK).body(settings);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateSettingsDetails(@RequestBody SettingsDto settingsDto){
		
		boolean isUpdated = iSettingsService.updateSettings(settingsDto);
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
	public ResponseEntity<ResponseDto> deleteSettingsDetails(@RequestParam int id){
		
		boolean isDeleted = iSettingsService.deleteSettings(id);
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
	public ApiResponse<List<Settings>> getAllSettings(){
	List<Settings> settings =	iSettingsService.fetchAllSettings();
	
	return new ApiResponse<List<Settings>>("200", "success", settings);
	}

}
