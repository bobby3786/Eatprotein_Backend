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

import com.authentication.dto.RoleDto;
import com.authentication.entity.Role;
import com.authentication.service.IRoleService;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ApiResponse;
import com.sharedLibrary.dto.ResponseDto;

@RestController
@RequestMapping("auth/role")
public class RoleController {
	
	@Autowired
	private IRoleService iRoleService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createRole(@RequestBody RoleDto roleDto){
		iRoleService.createRole(roleDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<Role> fetchRoleDetails(@RequestParam int id){
		
		Role role = iRoleService.fetchRole(id);
	return ResponseEntity.status(HttpStatus.OK).body(role);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateRoleDetails(@RequestBody RoleDto roleDto){
		
		boolean isUpdated = iRoleService.updateRole(roleDto);
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
	public ResponseEntity<ResponseDto> deleteRoleDetails(@RequestParam int id){
		
		boolean isDeleted = iRoleService.deleteRole(id);
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
	public ApiResponse<List<Role>> getAllTags(){
	List<Role> role =	iRoleService.fetchAllRoles();
	
	return new ApiResponse<List<Role>>("200", "success", role);
	}



}
