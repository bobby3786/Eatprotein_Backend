package com.payments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payments.dto.PaymentStoreDto;
import com.payments.entity.PaymentStore;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.payments.repository.PaymentStoreRepository;
import com.payments.service.IPaymentStoreService;


@Service
public class PaymentStoreServiceImpl implements IPaymentStoreService{
	
	@Autowired
	private PaymentStoreRepository paymentStoreRepository;

	@Override
	public void createPaymentStore(PaymentStoreDto paymentStoreDto) {

		PaymentStore paymentStore=new PaymentStore();
		ObjectEntityCheckutil.copyNonNullProperties(paymentStoreDto, paymentStore);
		paymentStoreRepository.save(paymentStore);
		
	}

	@Override
	public PaymentStore fetchPaymentStore(int id) {
	
		PaymentStore paymentStore = paymentStoreRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentStore", "id", id)
				);
		
		return paymentStore;
		
	}

	@Override
	public boolean updatePaymentStore(PaymentStoreDto paymentStoreDto) {
	
		PaymentStore paymentStore = paymentStoreRepository.findById(paymentStoreDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("PaymentStore", "id", paymentStoreDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(paymentStoreDto, paymentStore);
		paymentStoreRepository.save(paymentStore);
		
		return true;
	}

	@Override
	public boolean deletePaymentStore(int id) {

		paymentStoreRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentStore", "id", id)
				);
		paymentStoreRepository.deleteById(id);
		return true;
	}

	@Override
	public List<PaymentStore> fetchAllPaymentStores() {
		
		return paymentStoreRepository.findAll();
	}



}
