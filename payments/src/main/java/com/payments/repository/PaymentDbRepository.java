package com.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payments.entity.PaymentDb;


@Repository
public interface PaymentDbRepository extends JpaRepository<PaymentDb, Integer>{

}
