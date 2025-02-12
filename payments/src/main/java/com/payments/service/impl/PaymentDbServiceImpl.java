package com.payments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payments.dto.PaymentDbDto;
import com.payments.entity.PaymentDb;
import com.payments.repository.PaymentDbRepository;
import com.payments.service.IPaymentDbService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class PaymentDbServiceImpl implements IPaymentDbService{
	
	
	@Autowired
	private PaymentDbRepository paymentDbRepository;

	@Override
	public void createPaymentDb(PaymentDbDto paymentDbDto) {

		PaymentDb paymentDb=new PaymentDb();
		ObjectEntityCheckutil.copyNonNullProperties(paymentDbDto, paymentDb);
		paymentDbRepository.save(paymentDb);
		
	}

	@Override
	public PaymentDb fetchPaymentDb(int id) {
	
		PaymentDb paymentDb = paymentDbRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentDb", "id", id)
				);
		
		return paymentDb;
		
	}

	@Override
	public boolean updatePaymentDb(PaymentDbDto paymentDbDto) {
	
		PaymentDb paymentDb = paymentDbRepository.findById(paymentDbDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("paymentDb", "id", paymentDbDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(paymentDbDto, paymentDb);
		paymentDbRepository.save(paymentDb);
		
		return true;
	}

	@Override
	public boolean deletePaymentDb(int id) {

		paymentDbRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentDb", "id", id)
				);
		paymentDbRepository.deleteById(id);
		return true;
	}

	@Override
	public List<PaymentDb> fetchAllPaymentDbs() {
		
		return paymentDbRepository.findAll();
	}


	

}
