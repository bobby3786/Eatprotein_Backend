package com.apputil.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apputil.dto.BannerStoreDto;
import com.apputil.entity.BannerStore;
import com.apputil.repository.BannerStoreRepository;
import com.apputil.service.IBannerStoreService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class BannerStoreServiceImpl implements IBannerStoreService{
	
	
	@Autowired
	private BannerStoreRepository bannerStoreRepository;

	@Override
	public void createBannerStore(BannerStoreDto bannerStoreDto) {
      
		BannerStore bannerStore=new BannerStore();
		ObjectEntityCheckutil.copyNonNullProperties(bannerStoreDto, bannerStore);
		bannerStoreRepository.save(bannerStore);
		
	}

	@Override
	public BannerStore fetchBannerStore(int id) {
     	
		BannerStore bannerStore = bannerStoreRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("BannerStore", "id", id)
				);
		
		return bannerStore;
	}

	@Override
	public boolean updateBannerStore(BannerStoreDto bannerStoreDto) {

		BannerStore bannerStore = bannerStoreRepository.findById(bannerStoreDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("BannerStore", "id", bannerStoreDto.getId())
				);
		ObjectEntityCheckutil.copyNonNullProperties(bannerStoreDto, bannerStore);
		bannerStoreRepository.save(bannerStore);
		
		return true;
	}

	@Override
	public boolean deleteBannerStore(int id) {
		
		bannerStoreRepository.findById(id).orElseThrow(
	    		()-> new ResourceNotFoundException("BannerStore", "id", id)
	    		);
		bannerStoreRepository.deleteById(id);
	
		return true;
	}

	@Override
	public List<BannerStore> fetchAllBannerStores() {
		
		return bannerStoreRepository.findAll();
	}


}
