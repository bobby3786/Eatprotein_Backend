package com.payments.service;

import java.util.List;

import com.payments.dto.PaymentUtilDto;
import com.payments.entity.PaymentUtil;

public interface IPaymentUtilService {

	void createPaymentUtil(PaymentUtilDto paymentUtilDto);
	PaymentUtil fetchPaymentUtil(int id);
	boolean updatePaymentUtil(PaymentUtilDto paymentUtilDto);
	boolean deletePaymentUtil(int id);
	List<PaymentUtil> fetchAllPaymentUtils();
	
}
