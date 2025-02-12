package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.Promocode;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode, Integer>{

}
