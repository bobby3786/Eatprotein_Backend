package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Integer>{

}
