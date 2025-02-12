package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.WalletTransactionDto;
import com.authentication.entity.WalletTransaction;
import com.authentication.repository.WalletTransactionRepository;
import com.authentication.service.IWalletTransactionService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class WalletTransactionServiceImpl implements IWalletTransactionService{

	@Autowired
	private WalletTransactionRepository walletTransactionRepository;

	@Override
	public void createWalletTransaction(WalletTransactionDto walletTransactionDto) {

		WalletTransaction walletTransaction=new WalletTransaction();
		ObjectEntityCheckutil.copyNonNullProperties(walletTransactionDto, walletTransaction);
		walletTransactionRepository.save(walletTransaction);
		
	}

	@Override
	public WalletTransaction fetchWalletTransaction(int id) {
	
		WalletTransaction walletTransaction = walletTransactionRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("walletTransaction", "id", id)
				);
		
		return walletTransaction;
		
	}

	@Override
	public boolean updateWalletTransaction(WalletTransactionDto walletTransactionDto) {
	
		WalletTransaction walletTransaction = walletTransactionRepository.findById(walletTransactionDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("user", "id", walletTransactionDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(walletTransactionDto, walletTransaction);
		walletTransactionRepository.save(walletTransaction);
		
		return true;
	}

	@Override
	public boolean deleteWalletTransaction(int id) {

		walletTransactionRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("WalletTransaction", "id", id)
				);
		walletTransactionRepository.deleteById(id);
		return true;
	}

	@Override
	public List<WalletTransaction> fetchAllWalletTransactions() {
		
		return walletTransactionRepository.findAll();
	}

	
}
