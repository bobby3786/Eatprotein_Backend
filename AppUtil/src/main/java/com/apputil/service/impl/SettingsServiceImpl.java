package com.apputil.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apputil.dto.SettingsDto;
import com.apputil.entity.Settings;
import com.apputil.repository.SettingsRepository;
import com.apputil.service.ISettingsService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class SettingsServiceImpl implements ISettingsService{
	
	@Autowired
	private SettingsRepository settingsRepository;

	@Override
	public void createSettings(SettingsDto settingsDto) {
      
		Settings settings=new Settings();
		ObjectEntityCheckutil.copyNonNullProperties(settingsDto, settings);
		settingsRepository.save(settings);
		
	}

	@Override
	public Settings fetchSettings(int id) {
     	
		Settings settings = settingsRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Settings", "id", id)
				);
		
		return settings;
	}

	@Override
	public boolean updateSettings(SettingsDto settingsDto) {

		Settings settings = settingsRepository.findById(settingsDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("settings", "id", settingsDto.getId())
				);
		ObjectEntityCheckutil.copyNonNullProperties(settingsDto, settings);
		settingsRepository.save(settings);
		
		return true;
	}

	@Override
	public boolean deleteSettings(int id) {
		
		settingsRepository.findById(id).orElseThrow(
	    		()-> new ResourceNotFoundException("Settings", "id", id)
	    		);
		settingsRepository.deleteById(id);
	
		return true;
	}

	@Override
	public List<Settings> fetchAllSettings() {
		
		return settingsRepository.findAll();
	}


}
