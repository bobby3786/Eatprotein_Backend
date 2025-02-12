package com.authentication.service;

import java.util.List;

import com.authentication.dto.UserWalletDto;
import com.authentication.entity.UserWallet;

public interface IUserWalletService {
	
	void createUserWallet(UserWalletDto userDto);
	UserWallet fetchUserWallet(int id);
	boolean updateUserWallet(UserWalletDto userDto);
	boolean deleteUserWallet(int id);
	List<UserWallet> fetchAllUserWallets();

}
