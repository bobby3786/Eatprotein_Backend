package com.authentication.service;

import java.util.List;

import com.authentication.dto.UserDto;
import com.authentication.entity.User;

public interface IUserService {

	void createUser(UserDto userDto);
	User fetchUser(int id);
	boolean updateUser(UserDto userDto);
	boolean deleteUser(int id);
	List<User> fetchAllUsers();
	
}
