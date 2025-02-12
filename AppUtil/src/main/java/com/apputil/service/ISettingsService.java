package com.apputil.service;

import java.util.List;

import com.apputil.dto.SettingsDto;
import com.apputil.entity.Settings;

public interface ISettingsService {
	
	void createSettings(SettingsDto settingsDto);
	Settings fetchSettings(int id);
	boolean updateSettings(SettingsDto settingsDto);
	boolean deleteSettings(int id);
	List<Settings> fetchAllSettings();

}
