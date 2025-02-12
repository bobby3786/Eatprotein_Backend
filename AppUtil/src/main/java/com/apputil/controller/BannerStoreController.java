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

import com.apputil.dto.BannerStoreDto;
import com.apputil.entity.BannerStore;
import com.apputil.service.IBannerStoreService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;


@RestController
@RequestMapping("apputil/bannerstore")
public class BannerStoreController {
	
	@Autowired
	private IBannerStoreService iBannerStoreService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createBannerStore(@RequestBody BannerStoreDto bannerStoreDto){
		iBannerStoreService.createBannerStore(bannerStoreDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<BannerStore> fetchBannerStoreDetails(@RequestParam int id){
		BannerStore bannerStore=iBannerStoreService.fetchBannerStore(id);
		return ResponseEntity.status(HttpStatus.OK).body(bannerStore);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateBannerStoreDetails(@RequestBody BannerStoreDto bannerStoreDto){
		
		boolean isUpdated = iBannerStoreService.updateBannerStore(bannerStoreDto);
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
	public ResponseEntity<ResponseDto> deleteBannerStoreDetails(@RequestParam int id){
		
		boolean isDeleted = iBannerStoreService.deleteBannerStore(id);
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
	public ApiResponse<List<BannerStore>> getAllBannerStore(){
	List<BannerStore> bannerStore =	iBannerStoreService.fetchAllBannerStores();
	
	return new ApiResponse<List<BannerStore>>("200", "success", bannerStore);
	}

}
