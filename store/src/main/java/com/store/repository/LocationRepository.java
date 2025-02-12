package com.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.store.entity.Location;

@Repository
public interface LocationRepository extends MongoRepository<Location, String>{

}
