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

import com.catalog.dto.UomDto;
import com.catalog.entity.Uom;
import com.catalog.service.IUomService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;


@RestController
@RequestMapping("catalog/uom")
public class UomController {
	

	@Autowired
	private IUomService iUomService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUom(@RequestBody UomDto uomDto){
		iUomService.createUom(uomDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Uom> fetchUomDetails(@RequestParam int id){
		
		Uom uom = iUomService.fetchUom(id);
	return ResponseEntity.status(HttpStatus.OK).body(uom);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateUomDetails(@RequestBody UomDto uomDto){
		
		boolean isUpdated = iUomService.updateUom(uomDto);
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
	public ResponseEntity<ResponseDto> deleteUomDetails(@RequestParam int id){
		
		boolean isDeleted = iUomService.deleteUom(id);
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
	public ApiResponse<List<Uom>> getAllUoms(){
	List<Uom> uom =	iUomService.fetchAllUoms();
	
	return new ApiResponse<List<Uom>>("200", "success", uom);
	}



}
