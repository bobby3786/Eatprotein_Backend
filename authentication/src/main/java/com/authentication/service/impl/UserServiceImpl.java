package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.UserDto;
import com.authentication.entity.User;
import com.authentication.repository.UserRepository;
import com.authentication.service.IUserService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserServiceImpl implements IUserService{
	

	@Autowired
	private UserRepository userRepository;

	@Override
	public void createUser(UserDto userDto) {

		User user=new User();
		ObjectEntityCheckutil.copyNonNullProperties(userDto, user);
		userRepository.save(user);
		
	}

	@Override
	public User fetchUser(int id) {
	
		User user = userRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("User", "id", id)
				);
		
		return user;
		
	}

	@Override
	public boolean updateUser(UserDto userDto) {
	
		User user = userRepository.findById(userDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("user", "id", userDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userDto, user);
		userRepository.save(user);
		
		return true;
	}

	@Override
	public boolean deleteUser(int id) {

		userRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("user", "id", id)
				);
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public List<User> fetchAllUsers() {
		
		return userRepository.findAll();
	}




}
