package com.authentication.service;

import java.util.List;

import com.authentication.dto.RoleDto;
import com.authentication.entity.Role;

public interface IRoleService {
	
	void createRole(RoleDto roleDto);
	Role fetchRole(int id);
	boolean updateRole(RoleDto roleDto);
	boolean deleteRole(int id);
	List<Role> fetchAllRoles();

}
