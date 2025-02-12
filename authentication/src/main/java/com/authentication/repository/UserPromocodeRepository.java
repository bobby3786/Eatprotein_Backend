package com.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.entity.UserPromocode;

@Repository
public interface UserPromocodeRepository extends JpaRepository<UserPromocode, Integer>{

}
