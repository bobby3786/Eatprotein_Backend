package com.apputil.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apputil.dto.AVersionDto;
import com.apputil.entity.AppVersion;
import com.apputil.repository.AVersionRepository;
import com.apputil.service.IAppVersionService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;



@Service
public class AppVersionServiceImpl implements IAppVersionService{
	
	@Autowired
	private AVersionRepository  appVersionRepository;
	



	@Override
	public void createAppVersion(AVersionDto appVersionDto) {
		AppVersion appVersion = new AppVersion();
		ObjectEntityCheckutil.copyNonNullProperties(appVersionDto, appVersion);
		appVersionRepository.save(appVersion);
		
	}


	@Override
	public AppVersion fetchAppVersion(int id) {
		AppVersion appVersion = appVersionRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("AppVersion","id",id));
		return appVersion;
	}




	@Override
	public boolean updateAppVersion(AVersionDto appVersionDto) {
		
		
		
		AppVersion appVersion = appVersionRepository.findById(appVersionDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("AppVersion","id",appVersionDto.getId()));
		
		//AppVersionMapper.mapToAppVersion(appVersionDto, appVersion);
		ObjectEntityCheckutil.copyNonNullProperties(appVersionDto, appVersion);
		appVersionRepository.save(appVersion);
		
		
		return true;
	}




	@Override
	public boolean deleteAppVersion(int id) {
		 appVersionRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("AppVersion","id",id));
		appVersionRepository.deleteById(id);
	    
	
		return true;
	}




	@Override
	public List<AppVersion> fetchAllAppVersions() {
		
		return appVersionRepository.findAll();
	}



	
}
