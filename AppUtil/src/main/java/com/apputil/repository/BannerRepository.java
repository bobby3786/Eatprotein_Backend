package com.apputil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.apputil.entity.Banner;

import jakarta.transaction.Transactional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer>{
	
Optional<Banner>  findById(int id);
	
	@Transactional
	@Modifying
	void deleteById(int id);

}
