package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.CategoryTag;

@Repository
public interface CategoryTagRepository extends JpaRepository<CategoryTag, Integer>{

}
