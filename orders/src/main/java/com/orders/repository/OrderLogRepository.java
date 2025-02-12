package com.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orders.entity.OrderLog;

@Repository
public interface OrderLogRepository extends MongoRepository<OrderLog, String>{

}
