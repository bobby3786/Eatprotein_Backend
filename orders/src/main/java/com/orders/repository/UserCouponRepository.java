package com.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orders.entity.UserCoupon;

@Repository
public interface UserCouponRepository extends MongoRepository<UserCoupon, String>{

}
