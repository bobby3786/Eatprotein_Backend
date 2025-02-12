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

import com.catalog.dto.PromocodeDto;
import com.catalog.entity.Promocode;
import com.catalog.service.IPromocodeService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;


@RestController
@RequestMapping("catalog/promocode")
public class PromocodeController {
	
	@Autowired
	private IPromocodeService iPromocodeService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createPromocode(@RequestBody PromocodeDto promocodeDto){
		iPromocodeService.createPromocode(promocodeDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Promocode> fetchPromocodeDetails(@RequestParam int id){
		
		Promocode promocode = iPromocodeService.fetchPromocode(id);
	return ResponseEntity.status(HttpStatus.OK).body(promocode);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updatePromocodeDetails(@RequestBody PromocodeDto promocodeDto){
		
		boolean isUpdated = iPromocodeService.updatePromocode(promocodeDto);
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
	public ResponseEntity<ResponseDto> deletePromocodeDetails(@RequestParam int id){
		
		boolean isDeleted = iPromocodeService.deletePromocode(id);
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
	public ApiResponse<List<Promocode>> getAllPromocodes(){
	List<Promocode> promocode =	iPromocodeService.fetchAllPromocodes();
	
	return new ApiResponse<List<Promocode>>("200", "success", promocode);
	}




}
