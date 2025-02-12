package com.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orders.entity.OrderItem;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, String>{

}
