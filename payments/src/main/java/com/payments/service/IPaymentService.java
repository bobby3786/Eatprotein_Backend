package com.payments.service;

import java.util.List;

import com.payments.dto.PaymentDto;
import com.payments.entity.Payment;


public interface IPaymentService {
	
	void createPayment(PaymentDto paymentDto);
	Payment fetchPayment(int id);
	boolean updatePayment(PaymentDto paymentDto);
	boolean deletePayment(int id);
	List<Payment> fetchAllPayments();


}
