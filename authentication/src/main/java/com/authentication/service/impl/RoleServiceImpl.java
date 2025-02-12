package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.RoleDto;
import com.authentication.entity.Role;
import com.authentication.repository.RoleRepository;
import com.authentication.service.IRoleService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void createRole(RoleDto roleDto) {

		Role role=new Role();
		ObjectEntityCheckutil.copyNonNullProperties(roleDto, role);
		roleRepository.save(role);
		
	}

	@Override
	public Role fetchRole(int id) {
	
		Role role = roleRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Role", "id", id)
				);
		
		return role;
		
	}

	@Override
	public boolean updateRole(RoleDto roleDto) {
	
		Role role = roleRepository.findById(roleDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("role", "id", roleDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(roleDto, role);
		roleRepository.save(role);
		
		return true;
	}

	@Override
	public boolean deleteRole(int id) {

		roleRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("role", "id", id)
				);
		roleRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Role> fetchAllRoles() {
		
		return roleRepository.findAll();
	}



}
