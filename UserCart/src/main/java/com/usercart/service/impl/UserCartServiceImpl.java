package com.usercart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.usercart.dto.UserCartDto;
import com.usercart.entity.UserCart;
import com.usercart.repository.UserCartRepository;
import com.usercart.service.IUserCartService;



@Service
public class UserCartServiceImpl implements IUserCartService{
	
	@Autowired
	private UserCartRepository userCartRepository;

	@Override
	public void createUserCart(UserCartDto userCartDto) {

		UserCart userCart=new UserCart();
		ObjectEntityCheckutil.copyNonNullProperties(userCartDto, userCart);
		userCartRepository.save(userCart);
		
	}

	@Override
	public UserCart fetchUserCart(int id) {
	
		UserCart userCart = userCartRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("UserCart", "id", id)
				);
		
		return userCart;
		
	}

	@Override
	public boolean updateUserCart(UserCartDto userCartDto) {
	
		UserCart userCart = userCartRepository.findById(userCartDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("UserCart", "id", userCartDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userCartDto, userCart);
		userCartRepository.save(userCart);
		
		return true;
	}

	@Override
	public boolean deleteUserCart(int id) {

		userCartRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("UserCart", "id", id)
				);
		userCartRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserCart> fetchAllUserCarts() {
		
		return userCartRepository.findAll();
	}




}
