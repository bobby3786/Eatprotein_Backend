package com.authentication.service;

import java.util.List;

import com.authentication.dto.UserAddressDto;
import com.authentication.entity.UserAddress;

public interface IUserAddressService {
	
	void createUserAddress(UserAddressDto roleDto);
	UserAddress fetchUserAddress(int id);
	boolean updateUserAddress(UserAddressDto roleDto);
	boolean deleteUserAddress(int id);
	List<UserAddress> fetchAllUserAddresses();

}
