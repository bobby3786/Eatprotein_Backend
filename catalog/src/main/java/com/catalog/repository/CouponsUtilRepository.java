package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.CouponsUtil;

@Repository
public interface CouponsUtilRepository extends JpaRepository<CouponsUtil, Integer>{

}
