package com.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orders.entity.OrderPayment;

@Repository
public interface OrderPaymentRepository extends MongoRepository<OrderPayment, String>{

}
