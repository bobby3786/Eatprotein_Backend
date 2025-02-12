package com.payments.service;

import java.util.List;

import com.payments.dto.PaymentFoDto;
import com.payments.entity.PaymentFo;

public interface IPaymentFoService {
	
	void createPaymentFo(PaymentFoDto paymentFoDto);
	PaymentFo fetchPaymentFo(int id);
	boolean updatePaymentFo(PaymentFoDto paymentFoDto);
	boolean deletePaymentFo(int id);
	List<PaymentFo> fetchAllPaymentFos();

}
