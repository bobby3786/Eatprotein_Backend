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

import com.apputil.dto.BannerDto;
import com.apputil.entity.Banner;
import com.apputil.service.IBannerService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("apputil/banner")
public class BannerController {
	
	@Autowired
	private IBannerService iBannerService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createBanner(@RequestBody BannerDto bannerDto){
		iBannerService.createBanner(bannerDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Banner> fetchBannerDetails(@RequestParam int id){
		Banner banner=iBannerService.fetchBanner(id);
		
//		if (banner == null) {
//	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
		
		return ResponseEntity.status(HttpStatus.OK).body(banner);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateBAnnerDetails(@RequestBody BannerDto bannerDto){
		
		boolean isUpdated = iBannerService.updateBanner(bannerDto);
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
	public ResponseEntity<ResponseDto> deleteBannerDetails(@RequestParam int id){
		
		boolean isDeleted = iBannerService.deleteBanner(id);
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
	public ApiResponse<List<Banner>> getAllBanners(){
	List<Banner> banner =	iBannerService.fetchAllBanners();
	
	return new ApiResponse<List<Banner>>("200", "success", banner);
	}
}
