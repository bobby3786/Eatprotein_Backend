package com.orders.service;

import java.util.List;

import com.orders.dto.UserCouponDto;
import com.orders.entity.UserCoupon;

public interface IUserCouponService {
	
	void createUserCoupon(UserCouponDto userCouponDto);
	UserCoupon fetchUserCoupon(String id);
	boolean updateUserCoupon(UserCouponDto userCouponDto);
	boolean deleteUserCoupon(String id);
	List<UserCoupon> fetchAllUserCoupons();

}
