package com.authentication.controller;

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

import com.authentication.dto.UniqueCodeDto;
import com.authentication.entity.UniqueCode;
import com.authentication.service.IUniqueCodeService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/uniquecode")
public class UniqueCodeController {
	
	@Autowired
	private IUniqueCodeService iUniqueCodeService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUniqueCode(@RequestBody UniqueCodeDto uniqueCodeDto){
		iUniqueCodeService.createUniqueCode(uniqueCodeDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<UniqueCode> fetchUniqueCodeDetails(@RequestParam int id){
		
		UniqueCode uniqueCode = iUniqueCodeService.fetchUniqueCode(id);
	return ResponseEntity.status(HttpStatus.OK).body(uniqueCode);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUniqueCodeDetails(@RequestBody UniqueCodeDto uniqueCodeDto){
		
		boolean isUpdated = iUniqueCodeService.updateUniqueCode(uniqueCodeDto);
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
	public ResponseEntity<ResponseDto> deleteUniqueCodeDetails(@RequestParam int id){
		
		boolean isDeleted = iUniqueCodeService.deleteUniqueCode(id);
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
	public ApiResponse<List<UniqueCode>> getAllUniqueCodes(){
	List<UniqueCode> uniqueCode =	iUniqueCodeService.fetchAllUniqueCodes();
	
	return new ApiResponse<List<UniqueCode>>("200", "success", uniqueCode);
	}



}
