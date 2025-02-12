package com.authentication.service;

import java.util.List;

import com.authentication.dto.UserDeletedDto;
import com.authentication.entity.UserDeleted;

public interface IUserDeletedService {
	
	void createUserDeleted(UserDeletedDto userDeletedDto);
	UserDeleted fetchUserDeleted(int id);
	boolean updateUserDeleted(UserDeletedDto userDeletedDto);
	boolean deleteUserDeleted(int id);
	List<UserDeleted> fetchAllUsersDeleted();

}
