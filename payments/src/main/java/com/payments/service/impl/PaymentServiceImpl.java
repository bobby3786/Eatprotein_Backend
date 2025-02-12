package com.payments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payments.dto.PaymentDto;
import com.payments.entity.Payment;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.payments.repository.PaymentRepository;
import com.payments.service.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService{

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public void createPayment(PaymentDto paymentDto) {

		Payment payment=new Payment();
		ObjectEntityCheckutil.copyNonNullProperties(paymentDto, payment);
		paymentRepository.save(payment);
		
	}

	@Override
	public Payment fetchPayment(int id) {
	
		Payment payment = paymentRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Payment", "id", id)
				);
		
		return payment;
		
	}

	@Override
	public boolean updatePayment(PaymentDto paymentDto) {
	
		Payment payment = paymentRepository.findById(paymentDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("orderItem", "id", paymentDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(paymentDto, payment);
		paymentRepository.save(payment);
		
		return true;
	}

	@Override
	public boolean deletePayment(int id) {

		paymentRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Payment", "id", id)
				);
		paymentRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Payment> fetchAllPayments() {
		
		return paymentRepository.findAll();
	}


	
}
