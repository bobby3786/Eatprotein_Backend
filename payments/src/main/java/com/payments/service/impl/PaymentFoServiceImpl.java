package com.payments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payments.dto.PaymentFoDto;
import com.payments.entity.PaymentFo;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.payments.repository.PaymentFoRepository;
import com.payments.service.IPaymentFoService;

@Service
public class PaymentFoServiceImpl implements IPaymentFoService{
	
	@Autowired
	private PaymentFoRepository paymentFoRepository;

	@Override
	public void createPaymentFo(PaymentFoDto paymentFoDto) {

		PaymentFo paymentFo=new PaymentFo();
		ObjectEntityCheckutil.copyNonNullProperties(paymentFoDto, paymentFo);
		paymentFoRepository.save(paymentFo);
		
	}

	@Override
	public PaymentFo fetchPaymentFo(int id) {
	
		PaymentFo paymentFo = paymentFoRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentFO", "id", id)
				);
		
		return paymentFo;
		
	}

	@Override
	public boolean updatePaymentFo(PaymentFoDto paymentFoDto) {
	
		PaymentFo paymentFo = paymentFoRepository.findById(paymentFoDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("orderItem", "id", paymentFoDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(paymentFoDto, paymentFo);
		paymentFoRepository.save(paymentFo);
		
		return true;
	}

	@Override
	public boolean deletePaymentFo(int id) {

		paymentFoRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("PaymentFo", "id", id)
				);
		paymentFoRepository.deleteById(id);
		return true;
	}

	@Override
	public List<PaymentFo> fetchAllPaymentFos() {
		
		return paymentFoRepository.findAll();
	}



}
