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

import com.apputil.dto.AVersionDto;
import com.apputil.entity.AppVersion;
import com.apputil.service.IAppVersionService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;




@RestController
@RequestMapping("apputil/appversion")
public class AVersionController {
	@Autowired
	private IAppVersionService iAppVersionService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCategory(@RequestBody AVersionDto appVersionDto){
		
		iAppVersionService.createAppVersion(appVersionDto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201,Constants.MESSAGE_201));
		
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<AppVersion> fetchAppVersionDetails(@RequestParam Integer id){
		
		AppVersion appVersionDto = iAppVersionService.fetchAppVersion(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(appVersionDto);

	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAppVersionDetails(@RequestBody AVersionDto appVersionDto){
		
		boolean isUpdated = iAppVersionService.updateAppVersion(appVersionDto);
		
		if(isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200,Constants.MESSAGE_200));
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500,Constants.MESSAGE_500));
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteVersionDetails(@RequestParam int id){
		
		boolean isDeleted = iAppVersionService.deleteAppVersion(id);
		
		if(isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(Constants.STATUS_200,Constants.MESSAGE_200));
		}else {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(Constants.STATUS_500,Constants.MESSAGE_500));
		}
	}
	
	@GetMapping("/All")
    public ApiResponse<List<AppVersion>> getAllApVersions() {
        List<AppVersion> appVersions = iAppVersionService.fetchAllAppVersions();
        
        return new ApiResponse<List<AppVersion>>("200","success",appVersions);
    }
}
	
