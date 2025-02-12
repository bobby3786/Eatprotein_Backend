package com.apputil.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apputil.dto.BannerDto;
import com.apputil.entity.Banner;
import com.apputil.repository.BannerRepository;
import com.apputil.service.IBannerService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class BannerServiceImpl implements IBannerService{
	
	@Autowired
	private BannerRepository bannerRepository;

	@Override
	public void createBanner(BannerDto bannerDto) {
      
		Banner banner=new Banner();
		ObjectEntityCheckutil.copyNonNullProperties(bannerDto, banner);
		bannerRepository.save(banner);
		
	}

	@Override
	public Banner fetchBanner(int id) {
     	
		Banner banner = bannerRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Banner", "id", id)
				);
		
		return banner;
	}

	@Override
	public boolean updateBanner(BannerDto bannerDto) {

		Banner banner = bannerRepository.findById(bannerDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("Banner", "id", bannerDto.getId())
				);
		ObjectEntityCheckutil.copyNonNullProperties(bannerDto, banner);
		bannerRepository.save(banner);
		
		return true;
	}

	@Override
	public boolean deleteBanner(int id) {
		
	    bannerRepository.findById(id).orElseThrow(
	    		()-> new ResourceNotFoundException("Banner", "id", id)
	    		);
	    bannerRepository.deleteById(id);
	
		return true;
	}

	@Override
	public List<Banner> fetchAllBanners() {
		
		return bannerRepository.findAll();
	}

}
