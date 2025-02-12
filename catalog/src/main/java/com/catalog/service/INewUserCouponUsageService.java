package com.catalog.service;

import java.util.List;

import com.catalog.dto.NewUserCouponUsageDto;
import com.catalog.entity.NewUserCouponUsage;

public interface INewUserCouponUsageService {
	
	void createNewUserCouponUsage(NewUserCouponUsageDto newUserCouponUsageDto);
	NewUserCouponUsage fetchNewUserCouponUsage(int id);
	boolean updateNewUserCouponUsage(NewUserCouponUsageDto newUserCouponUsageDto);
	boolean deleteNewUserCouponUsage(int id);
	List<NewUserCouponUsage> fetchAllNewUserCouponUsages();

}
