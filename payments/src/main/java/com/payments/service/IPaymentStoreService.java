package com.payments.service;

import java.util.List;

import com.payments.dto.PaymentStoreDto;
import com.payments.entity.PaymentStore;

public interface IPaymentStoreService {
	
	void createPaymentStore(PaymentStoreDto paymentStoreDto);
	PaymentStore fetchPaymentStore(int id);
	boolean updatePaymentStore(PaymentStoreDto paymentStoreDto);
	boolean deletePaymentStore(int id);
	List<PaymentStore> fetchAllPaymentStores();

}
