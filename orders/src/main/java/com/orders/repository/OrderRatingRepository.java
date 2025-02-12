package com.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.orders.entity.OrderRating;

@Service
public interface OrderRatingRepository extends MongoRepository<OrderRating, String>{

}
