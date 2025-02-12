package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.ProductRating;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Integer>{

}
