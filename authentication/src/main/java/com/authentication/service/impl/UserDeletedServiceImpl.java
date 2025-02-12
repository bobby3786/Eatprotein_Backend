package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.UserDeletedDto;
import com.authentication.entity.UserDeleted;
import com.authentication.repository.UserDeletedRepository;
import com.authentication.service.IUserDeletedService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserDeletedServiceImpl implements IUserDeletedService{
	
	@Autowired
	private UserDeletedRepository userDeletedRepository;

	@Override
	public void createUserDeleted(UserDeletedDto userDeletedDto) {

		UserDeleted userDeleted=new UserDeleted();
		ObjectEntityCheckutil.copyNonNullProperties(userDeletedDto, userDeleted);
		userDeletedRepository.save(userDeleted);
		
	}

	@Override
	public UserDeleted fetchUserDeleted(int id) {
	
		UserDeleted userDeleted = userDeletedRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userDeleted", "id", id)
				);
		
		return userDeleted;
		
	}

	@Override
	public boolean updateUserDeleted(UserDeletedDto userDeletedDto) {
	
		UserDeleted userDeleted = userDeletedRepository.findById(userDeletedDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("UserDeleted", "id", userDeletedDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userDeletedDto, userDeleted);
		userDeletedRepository.save(userDeleted);
		
		return true;
	}

	@Override
	public boolean deleteUserDeleted(int id) {

		userDeletedRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("UserDeleted", "id", id)
				);
		userDeletedRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserDeleted> fetchAllUsersDeleted() {
		
		return userDeletedRepository.findAll();
	}



}
