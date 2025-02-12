package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.UserRecentView;


@Repository
public interface UserRecentViewRepository extends JpaRepository<UserRecentView, Integer>{

}
