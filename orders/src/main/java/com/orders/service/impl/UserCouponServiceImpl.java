package com.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.dto.OrderRatingDto;
import com.orders.dto.UserCouponDto;
import com.orders.entity.OrderRating;
import com.orders.entity.UserCoupon;
import com.orders.repository.OrderRatingRepository;
import com.orders.repository.UserCouponRepository;
import com.orders.service.IUserCouponService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserCouponServiceImpl implements IUserCouponService{
	
	@Autowired
	private UserCouponRepository userCouponRepository;

	@Override
	public void createUserCoupon(UserCouponDto userCouponDto) {

		UserCoupon userCoupon=new UserCoupon();
		ObjectEntityCheckutil.copyNonNullProperties(userCouponDto, userCoupon);
		userCouponRepository.save(userCoupon);
		
	}

	@Override
	public UserCoupon fetchUserCoupon(String id) {
	
		UserCoupon userCoupon = userCouponRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("userCoupon", "id", id)
				);
		
		return userCoupon;
		
	}

	@Override
	public boolean updateUserCoupon(UserCouponDto userCouponDto) {
	
		UserCoupon userCoupon = userCouponRepository.findById(userCouponDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", userCouponDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userCouponDto, userCoupon);
		userCouponRepository.save(userCoupon);
		
		return true;
	}

	@Override
	public boolean deleteUserCoupon(String id) {

		userCouponRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("UserCoupon", "id", id)
				);
		userCouponRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserCoupon> fetchAllUserCoupons() {
		
		return userCouponRepository.findAll();
	}



}
