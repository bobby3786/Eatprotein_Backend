package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.UserRecentViewDto;
import com.catalog.entity.UserRecentView;
import com.catalog.repository.UserRecentViewRepository;
import com.catalog.service.IUserRecentViewService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserRecentViewServiceImpl implements IUserRecentViewService{
	
	@Autowired
	private UserRecentViewRepository userRecentViewRepository;

	@Override
	public void createUserRecentView(UserRecentViewDto userRecentViewDto) {

		UserRecentView userRecentView=new UserRecentView();
		ObjectEntityCheckutil.copyNonNullProperties(userRecentViewDto, userRecentView);
		userRecentViewRepository.save(userRecentView);
		
	}

	@Override
	public UserRecentView fetchUserRecentView(int id) {
	
		UserRecentView userRecentView = userRecentViewRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userRecentView", "id", id)
				);
		
		return userRecentView;
		
	}

	@Override
	public boolean updateUserRecentView(UserRecentViewDto userRecentViewDto) {
	
		UserRecentView userRecentView = userRecentViewRepository.findById(userRecentViewDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("userRecentView", "id", userRecentViewDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userRecentViewDto, userRecentView);
		userRecentViewRepository.save(userRecentView);
		
		return true;
	}

	@Override
	public boolean deleteUserRecentView(int id) {

		userRecentViewRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userRecentView", "id", id)
				);
		userRecentViewRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserRecentView> fetchAllUserRecentViews() {
		
		return userRecentViewRepository.findAll();
	}



}
