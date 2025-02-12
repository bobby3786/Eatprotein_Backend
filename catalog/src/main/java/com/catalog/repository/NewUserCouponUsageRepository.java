package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.NewUserCouponUsage;

@Repository
public interface NewUserCouponUsageRepository extends JpaRepository<NewUserCouponUsage, Integer>{

}
