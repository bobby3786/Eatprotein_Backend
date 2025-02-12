package com.authentication.service;

import java.util.List;

import com.authentication.dto.WalletTransactionDto;
import com.authentication.entity.WalletTransaction;

public interface IWalletTransactionService {
	
	void createWalletTransaction(WalletTransactionDto walletTransactionDto);
	WalletTransaction fetchWalletTransaction(int id);
	boolean updateWalletTransaction(WalletTransactionDto walletTransactionDto);
	boolean deleteWalletTransaction(int id);
	List<WalletTransaction> fetchAllWalletTransactions();

}
