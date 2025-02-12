package com.catalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.catalog.entity.Brand;
import com.catalog.entity.Category;
import com.catalog.entity.Product;
import com.catalog.entity.SubCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
//	@Query("SELECT p FROM PRODUCT p WHERE p.CATEGORYID=?1 OR p.SUBCATEGORYID=?2 OR p.BRANDID=?3")
	List<Product> findByCategoryIdOrSubcategoryIdOrBrandId(Integer category,Integer subcategoryId,Integer brandId);

}
