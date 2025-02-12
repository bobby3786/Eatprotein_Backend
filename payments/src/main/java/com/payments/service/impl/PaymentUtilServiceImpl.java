package com.payments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payments.dto.PaymentUtilDto;
import com.payments.entity.PaymentUtil;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.payments.repository.PaymentUtilRepository;
import com.payments.service.IPaymentUtilService;

@Service
public class PaymentUtilServiceImpl implements IPaymentUtilService{
	
	@Autowired
	private PaymentUtilRepository paymentUtilRepository;

	@Override
	public void createPaymentUtil(PaymentUtilDto paymentUtilDto) {

		PaymentUtil paymentUtil=new PaymentUtil();
		ObjectEntityCheckutil.copyNonNullProperties(paymentUtilDto, paymentUtil);
		paymentUtilRepository.save(paymentUtil);
		
	}

	@Override
	public PaymentUtil fetchPaymentUtil(int id) {
	
		PaymentUtil paymentUtil = paymentUtilRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentUtil", "id", id)
				);
		
		return paymentUtil;
		
	}

	@Override
	public boolean updatePaymentUtil(PaymentUtilDto paymentUtilDto) {
	
		PaymentUtil paymentUtil = paymentUtilRepository.findById(paymentUtilDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("orderItem", "id", paymentUtilDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(paymentUtilDto, paymentUtil);
		paymentUtilRepository.save(paymentUtil);
		
		return true;
	}

	@Override
	public boolean deletePaymentUtil(int id) {

		paymentUtilRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentUtil", "id", id)
				);
		paymentUtilRepository.deleteById(id);
		return true;
	}

	@Override
	public List<PaymentUtil> fetchAllPaymentUtils() {
		
		return paymentUtilRepository.findAll();
	}



}
