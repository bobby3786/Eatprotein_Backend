package com.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.store.entity.ProductPrice;


@Repository
public interface ProductPriceRepository extends MongoRepository<ProductPrice, String>{

}
