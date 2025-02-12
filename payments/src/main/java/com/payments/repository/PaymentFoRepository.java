package com.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payments.entity.PaymentFo;

@Repository
public interface PaymentFoRepository extends JpaRepository<PaymentFo, Integer>{

}
