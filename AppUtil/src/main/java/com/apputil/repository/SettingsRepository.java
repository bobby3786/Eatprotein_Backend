package com.apputil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.apputil.entity.Settings;

@Service
public interface SettingsRepository extends JpaRepository<Settings, Integer>{

}
