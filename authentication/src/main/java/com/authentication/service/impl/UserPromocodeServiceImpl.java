package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.UserPromocodeDto;
import com.authentication.entity.UserPromocode;
import com.authentication.repository.UserPromocodeRepository;
import com.authentication.service.IUserPromocodeService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserPromocodeServiceImpl implements IUserPromocodeService {

	@Autowired
	private UserPromocodeRepository userPromocodeRepository;

	@Override
	public void createUserPromocode(UserPromocodeDto userPromocodeDto) {

		UserPromocode userPromocode=new UserPromocode();
		ObjectEntityCheckutil.copyNonNullProperties(userPromocodeDto, userPromocode);
		userPromocodeRepository.save(userPromocode);
		
	}

	@Override
	public UserPromocode fetchUserPromocode(int id) {
	
		UserPromocode userPromocode = userPromocodeRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userPromocode", "id", id)
				);
		
		return userPromocode;
		
	}

	@Override
	public boolean updateUserPromocode(UserPromocodeDto userPromocodeDto) {
	
		UserPromocode userPromocode = userPromocodeRepository.findById(userPromocodeDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("role", "id", userPromocodeDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userPromocodeDto, userPromocode);
		userPromocodeRepository.save(userPromocode);
		
		return true;
	}

	@Override
	public boolean deleteUserPromocode(int id) {

		userPromocodeRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userPromocode", "id", id)
				);
		userPromocodeRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserPromocode> fetchAllUserPromocodes() {
		
		return userPromocodeRepository.findAll();
	}


	
}
