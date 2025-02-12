package com.apputil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.apputil.entity.AppVersion;

import jakarta.transaction.Transactional;



@Repository
public interface AVersionRepository extends JpaRepository<AppVersion, Integer>{

Optional<AppVersion>  findById(Integer id);
	
	@Transactional
	@Modifying
	void deleteById(int id);
	
}
