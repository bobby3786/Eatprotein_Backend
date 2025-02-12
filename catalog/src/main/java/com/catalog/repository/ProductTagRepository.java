package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.ProductTag;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Integer>{

}
