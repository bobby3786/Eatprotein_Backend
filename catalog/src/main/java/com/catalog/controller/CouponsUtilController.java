package com.catalog.controller;

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

import com.catalog.dto.CouponsUtilDto;
import com.catalog.entity.CouponsUtil;
import com.catalog.service.ICouponsUtilService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;


@RestController
@RequestMapping("catalog/couponsutil")
public class CouponsUtilController {
	
	@Autowired
	private ICouponsUtilService iCouponsUtilService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCouponsUtil(@RequestBody CouponsUtilDto couponsUtilDto){
		iCouponsUtilService.createCouponsUtil(couponsUtilDto);
		return ResponseEntity
		                   .status(HttpStatus.CREATED)
		                   .body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<CouponsUtil> fetchCouponsUtilDetails(@RequestParam int id){
		CouponsUtil couponsUtil = iCouponsUtilService.fetchCouponsUtil(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(couponsUtil);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateCouponsUtil(@RequestBody CouponsUtilDto couponsUtilDto){
	boolean isUpdated =	iCouponsUtilService.updateCouponsUtil(couponsUtilDto);
	
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
	public ResponseEntity<ResponseDto> deleteCouponsUtilDetails(@RequestParam int id){
		
		boolean isDeleted = iCouponsUtilService.deleteCouponsUtil(id);
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
	public ApiResponse<List<CouponsUtil>> getAllCouponsUtils(){
	List<CouponsUtil> couponsUtil =	iCouponsUtilService.fetchAllCouponsUtils();
	
	return new ApiResponse<List<CouponsUtil>>("200", "success", couponsUtil);
	}



}
