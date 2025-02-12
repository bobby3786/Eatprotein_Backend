package com.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payments.entity.PaymentUtil;

@Repository
public interface PaymentUtilRepository extends JpaRepository<PaymentUtil, Integer>{

}
