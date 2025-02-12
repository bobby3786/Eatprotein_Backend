package com.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;
import com.store.dto.StoreDto;
import com.store.entity.Store;
import com.store.service.IStoreService;

@RestController
@RequestMapping("store/store")
public class StoreController {
	
	@Autowired
	private IStoreService iStoreService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createStore(@RequestBody StoreDto storeDto){
		iStoreService.createStore(storeDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Store> fetchStoreDetails(@RequestParam("id") String id){
		
		Store store = iStoreService.fetchStore(id);
	return ResponseEntity.status(HttpStatus.OK).body(store);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateStoreDetails(@RequestBody StoreDto storeDto){
		
		boolean isUpdated = iStoreService.updateStore(storeDto);
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
	public ResponseEntity<ResponseDto> deleteStoreDetails(@RequestParam("id") String id){
		
		boolean isDeleted = iStoreService.deleteStore(id);
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
	public ApiResponse<List<Store>> getAllStores(){
	List<Store> store =	iStoreService.fetchAllStores();
	
	return new ApiResponse<List<Store>>("200", "success", store);
	}

	@GetMapping("/{field}")
	public List<Store> getStoreWithField(@PathVariable String field){
		return iStoreService.getStoresWithField(field);
	}
	
	@GetMapping("/pagination/{offset}/{pageSize}")
	public Page<Store> getStoreWithPaginationAndSorting(@PathVariable int offset,@PathVariable int pageSize){
		Page<Store> stores = iStoreService.getStoresWithPaginationAndSortingFields(offset,pageSize);
		
		return stores;
	}
	
	@GetMapping("/pagination/{offset}/{pageSize}/{field}")
	public Page<Store> getStoreWithPaginationAndSortingField(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
		Page<Store> stores = iStoreService.getStoresWithPaginationAndSortingField(offset,pageSize,field);
		
		return stores;
	}


}
