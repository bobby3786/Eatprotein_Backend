package com.apputil.service.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apputil.dto.SettingsDto;
import com.apputil.entity.Settings;
import com.apputil.repository.SettingsRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SettingsServiceImplTest {

    @Mock
    private SettingsRepository settingsRepository;

    @InjectMocks
    private SettingsServiceImpl settingsService;
    

    @Test
    void testCreateSettings() {
    	
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setPlatformFee(100);
        settingsDto.setPackageCharge(200);
        settingsDto.setUtility(300.00);
        
        
        System.out.println("hiii hellooo....");

        Settings settings = new Settings();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(settingsDto, settings))
                        .thenAnswer(invocation -> null);

            when(settingsRepository.save(any(Settings.class))).thenReturn(settings);

            settingsService.createSettings(settingsDto);

            verify(settingsRepository, times(1)).save(any(Settings.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(settingsDto, settings), times(1));
        }
    }
    
    @Test
    void testCreateSettings_NullDto() {
       
        SettingsDto settingsDto = null;

        assertThrows(IllegalArgumentException.class, () -> settingsService.createSettings(settingsDto));
        verify(settingsRepository, never()).save(any(Settings.class));
    }
    
    @Test
    void testFetchSettings() {
     
        int id = 1; 

        Settings settings = new Settings();
        settings.setId(id);
        settings.setPackageCharge(100); 


       when(settingsRepository.findById(id)).thenReturn(Optional.of(settings));

        Settings result = settingsService.fetchSettings(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(100, result.getPackageCharge()); 
        verify(settingsRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchSettingsNotFound() {
        int id = 1;

        when(settingsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> settingsService.fetchSettings(id));
        verify(settingsRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateSettings() {
        
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setId(1); 
        settingsDto.setPackageCharge(200);

        Settings existingSettings = new Settings();
        existingSettings.setId(1); 
        existingSettings.setPackageCharge(100); 

        when(settingsRepository.findById(settingsDto.getId())).thenReturn(Optional.of(existingSettings));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(settingsDto, existingSettings))
                        .thenAnswer(invocation -> null);

            when(settingsRepository.save(existingSettings)).thenReturn(existingSettings);

            boolean result = settingsService.updateSettings(settingsDto);

            assertTrue(result);
            verify(settingsRepository, times(1)).findById(settingsDto.getId());
            verify(settingsRepository, times(1)).save(existingSettings);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(settingsDto, existingSettings), times(1));
        }
    }


    @Test
    void testUpdateSettingsNotFound() {
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setId(1);

        when(settingsRepository.findById(settingsDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> settingsService.updateSettings(settingsDto));
        verify(settingsRepository, times(1)).findById(settingsDto.getId());
    }

    @Test
    void testDeleteSettings() {
        int id = 1;
        Settings settings = new Settings();
        settings.setId(id);

        when(settingsRepository.findById(id)).thenReturn(Optional.of(settings));
        doNothing().when(settingsRepository).deleteById(id);

        boolean result = settingsService.deleteSettings(id);

        assertTrue(result);
        verify(settingsRepository, times(1)).findById(id);
        verify(settingsRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteSettingsNotFound() {
        int id = 1;

        when(settingsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> settingsService.deleteSettings(id));
        verify(settingsRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllSettings() {
        List<Settings> settingsList = new ArrayList<>();
        settingsList.add(new Settings());
        settingsList.add(new Settings());

        when(settingsRepository.findAll()).thenReturn(settingsList);

        List<Settings> result = settingsService.fetchAllSettings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(settingsRepository, times(1)).findAll();
    }
}

