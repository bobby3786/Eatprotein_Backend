package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.store.entity.StoreRating;

@Repository
public interface StoreRatingRepository extends MongoRepository<StoreRating, String>{

}
