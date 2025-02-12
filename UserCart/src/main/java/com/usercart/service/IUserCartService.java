package com.usercart.service;

import java.util.List;

import com.usercart.dto.UserCartDto;
import com.usercart.entity.UserCart;


public interface IUserCartService {
	
	void createUserCart(UserCartDto locationDto);
	UserCart fetchUserCart(int id);
	boolean updateUserCart(UserCartDto locationDto);
	boolean deleteUserCart(int id);
	List<UserCart> fetchAllUserCarts();

}
