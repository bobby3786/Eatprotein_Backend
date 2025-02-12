package com.payments.service;

import java.util.List;

import com.payments.dto.PaymentDbDto;
import com.payments.entity.PaymentDb;

public interface IPaymentDbService {
	
	void createPaymentDb(PaymentDbDto paymentDbDto);
	PaymentDb fetchPaymentDb(int id);
	boolean updatePaymentDb(PaymentDbDto paymentDbDto);
	boolean deletePaymentDb(int id);
	List<PaymentDb> fetchAllPaymentDbs();

}
