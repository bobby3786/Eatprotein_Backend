package com.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orders.entity.UserOrder;

@Repository
public interface UserOrderRepository extends MongoRepository<UserOrder, String>{

}
