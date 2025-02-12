package com.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.entity.UserDeleted;

@Repository
public interface UserDeletedRepository extends JpaRepository<UserDeleted, Integer>{

}
