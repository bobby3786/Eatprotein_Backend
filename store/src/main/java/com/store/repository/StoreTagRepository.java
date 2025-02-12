package com.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.store.entity.StoreTag;

@Repository
public interface StoreTagRepository extends MongoRepository<StoreTag, String>{

}
