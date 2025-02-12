package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.UserAddressDto;
import com.authentication.entity.UserAddress;
import com.authentication.repository.UserAddressRepository;
import com.authentication.service.IUserAddressService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserAddressServiceImpl implements IUserAddressService{
	
	@Autowired
	private UserAddressRepository userAddressRepository;

	@Override
	public void createUserAddress(UserAddressDto userAddressDto) {

		UserAddress userAddress=new UserAddress();
		ObjectEntityCheckutil.copyNonNullProperties(userAddressDto, userAddress);
		userAddressRepository.save(userAddress);
		
	}

	@Override
	public UserAddress fetchUserAddress(int id) {
	
		UserAddress userAddress = userAddressRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userAddress", "id", id)
				);
		
		return userAddress;
		
	}

	@Override
	public boolean updateUserAddress(UserAddressDto userAddressDto) {
	
		UserAddress userAddress = userAddressRepository.findById(userAddressDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("userAddress", "id", userAddressDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userAddressDto, userAddress);
		userAddressRepository.save(userAddress);
		
		return true;
	}

	@Override
	public boolean deleteUserAddress(int id) {

		userAddressRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("UserAddress", "id", id)
				);
		userAddressRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserAddress> fetchAllUserAddresses() {
		
		return userAddressRepository.findAll();
	}



}
