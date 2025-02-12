package com.authentication.service;

import java.util.List;

import com.authentication.dto.DeletedUsersDto;
import com.authentication.entity.DeletedUsers;


public interface IDeletedUsersService {
	
	void createDeletedUsers(DeletedUsersDto deletedUsersDto);
	DeletedUsers fetchDeletedUsers(int id);
	boolean updateDeletedUsers(DeletedUsersDto deletedUsersDto);
	boolean deleteDeletedUsers(int id);
	List<DeletedUsers> fetchAllDeletedUsers();
	
	

}
