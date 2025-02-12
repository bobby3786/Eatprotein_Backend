package com.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payments.entity.PaymentStore;

@Repository
public interface PaymentStoreRepository extends JpaRepository<PaymentStore, Integer>{

}
