package com.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orders.entity.OrderAddress;

@Repository
public interface OrderAddressRepository extends MongoRepository<OrderAddress, String>{

}
