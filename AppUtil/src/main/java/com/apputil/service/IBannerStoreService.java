package com.apputil.service;

import java.util.List;

import com.apputil.dto.BannerStoreDto;
import com.apputil.entity.BannerStore;

public interface IBannerStoreService {
	
	void createBannerStore(BannerStoreDto bannerStoreDto);
	BannerStore fetchBannerStore(int id);
	boolean updateBannerStore(BannerStoreDto bannerStoreDto);
	boolean deleteBannerStore(int id);
	List<BannerStore> fetchAllBannerStores();


}
