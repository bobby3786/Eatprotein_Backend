package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.NewUserCouponUsageDto;
import com.catalog.entity.NewUserCouponUsage;
import com.catalog.repository.NewUserCouponUsageRepository;
import com.catalog.service.INewUserCouponUsageService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class NewUserCouponUsageServiceImpl implements INewUserCouponUsageService{

	@Autowired
	private NewUserCouponUsageRepository newUserCouponUsageRepository;

	@Override
	public void createNewUserCouponUsage(NewUserCouponUsageDto newUserCouponUsageDto) {

		NewUserCouponUsage newUserCouponUsage=new NewUserCouponUsage();
		ObjectEntityCheckutil.copyNonNullProperties(newUserCouponUsageDto, newUserCouponUsage);
		newUserCouponUsageRepository.save(newUserCouponUsage);
		
	}

	@Override
	public NewUserCouponUsage fetchNewUserCouponUsage(int id) {
	
		NewUserCouponUsage newUserCouponUsage = newUserCouponUsageRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("NewUserCouponUsage", "id", id)
				);
		
		return newUserCouponUsage;
		
	}

	@Override
	public boolean updateNewUserCouponUsage(NewUserCouponUsageDto newUserCouponUsageDto) {
	
		NewUserCouponUsage newUserCouponUsage = newUserCouponUsageRepository.findById(newUserCouponUsageDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("NewUserCouponUsage", "id", newUserCouponUsageDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(newUserCouponUsageDto, newUserCouponUsage);
		newUserCouponUsageRepository.save(newUserCouponUsage);
		
		return true;
	}

	@Override
	public boolean deleteNewUserCouponUsage(int id) {

		newUserCouponUsageRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("NewUserCouponUsage", "id", id)
				);
		newUserCouponUsageRepository.deleteById(id);
		return true;
	}

	@Override
	public List<NewUserCouponUsage> fetchAllNewUserCouponUsages() {
		
		return newUserCouponUsageRepository.findAll();
	}


}
