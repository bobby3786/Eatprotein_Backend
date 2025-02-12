package com.usercart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usercart.entity.UserCart;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Integer>{

}
