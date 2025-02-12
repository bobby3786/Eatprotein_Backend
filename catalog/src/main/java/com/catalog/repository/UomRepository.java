package com.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.entity.Uom;

@Repository
public interface UomRepository extends JpaRepository<Uom, Integer>{

}
