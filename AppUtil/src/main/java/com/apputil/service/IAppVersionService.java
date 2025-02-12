package com.apputil.service;

import java.util.List;

import com.apputil.dto.AVersionDto;
import com.apputil.entity.AppVersion;



public interface IAppVersionService {
	
	void createAppVersion(AVersionDto appVersionDto);
	AppVersion fetchAppVersion(int  id);
	boolean updateAppVersion(AVersionDto appVersionDto);
	boolean deleteAppVersion(int id);
	List<AppVersion> fetchAllAppVersions();

}
