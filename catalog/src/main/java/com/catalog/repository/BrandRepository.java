package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{

}
