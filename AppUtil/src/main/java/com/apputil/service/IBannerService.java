package com.apputil.service;

import java.util.List;

import com.apputil.dto.BannerDto;
import com.apputil.entity.Banner;

public interface IBannerService {
	
	void createBanner(BannerDto bannerDto);
	Banner fetchBanner(int id);
	boolean updateBanner(BannerDto bannerDto);
	boolean deleteBanner(int id);
	List<Banner> fetchAllBanners();


}
