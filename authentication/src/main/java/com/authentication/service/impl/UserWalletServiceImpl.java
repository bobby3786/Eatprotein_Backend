package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.UserWalletDto;
import com.authentication.entity.UserWallet;
import com.authentication.repository.UserWalletRepository;
import com.authentication.service.IUserWalletService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UserWalletServiceImpl implements IUserWalletService{
	
	@Autowired
	private UserWalletRepository userWalletRepository;

	@Override
	public void createUserWallet(UserWalletDto userWalletDto) {

		UserWallet userWallet=new UserWallet();
		ObjectEntityCheckutil.copyNonNullProperties(userWalletDto, userWallet);
		userWalletRepository.save(userWallet);
		
	}

	@Override
	public UserWallet fetchUserWallet(int id) {
	
		UserWallet userWallet = userWalletRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userWallet", "id", id)
				);
		
		return userWallet;
		
	}

	@Override
	public boolean updateUserWallet(UserWalletDto userWalletDto) {
	
		UserWallet userWallet = userWalletRepository.findById(userWalletDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("userWallet", "id", userWalletDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(userWalletDto, userWallet);
		userWalletRepository.save(userWallet);
		
		return true;
	}

	@Override
	public boolean deleteUserWallet(int id) {

		userWalletRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("userWallet", "id", id)
				);
		userWalletRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserWallet> fetchAllUserWallets() {
		
		return userWalletRepository.findAll();
	}




}
