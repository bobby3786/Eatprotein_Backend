package com.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.entity.UniqueCode;

@Repository
public interface UniqueCodeRepository extends JpaRepository<UniqueCode, Integer>{

}
