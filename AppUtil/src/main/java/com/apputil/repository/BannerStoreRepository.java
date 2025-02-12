package com.apputil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apputil.entity.BannerStore;

@Repository
public interface BannerStoreRepository extends JpaRepository<BannerStore, Integer>{

}
