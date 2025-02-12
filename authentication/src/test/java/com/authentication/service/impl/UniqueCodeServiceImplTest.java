package com.authentication.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.authentication.dto.UniqueCodeDto;
import com.authentication.entity.UniqueCode;
import com.authentication.repository.UniqueCodeRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public  class UniqueCodeServiceImplTest {

	@Mock
    private UniqueCodeRepository uniqueCodeRepository;

    @InjectMocks
    private UniqueCodeServiceImpl uniqueCodeService;
    

    @Test
    void testCreateUniqueCode() {
    	
    	 UniqueCodeDto uniqueCodeDto = new UniqueCodeDto();
    	 uniqueCodeDto.setRefCode(456);
        
        
        UniqueCode uniqueCode = new UniqueCode();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(uniqueCodeDto, uniqueCode))
                        .thenAnswer(invocation -> null);

            when(uniqueCodeRepository.save(any(UniqueCode.class))).thenReturn(uniqueCode);

            uniqueCodeService.createUniqueCode(uniqueCodeDto);

            verify(uniqueCodeRepository, times(1)).save(any(UniqueCode.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(uniqueCodeDto, uniqueCode), times(1));
        }
    }
    
    @Test
    void testCreateUniqueCode_NullDto() {
       
        UniqueCodeDto uniqueCodeDto = null;

        assertThrows(IllegalArgumentException.class, () -> uniqueCodeService.createUniqueCode(uniqueCodeDto));
        verify(uniqueCodeRepository, never()).save(any(UniqueCode.class));
    }
    
    @Test
    void testFetchUniqueCode() {
     
        int id = 1; 

        UniqueCode uniqueCode = new UniqueCode();
        uniqueCode.setId(id);
        uniqueCode.setRefCode(456);


       when(uniqueCodeRepository.findById(id)).thenReturn(Optional.of(uniqueCode));

        UniqueCode result = uniqueCodeService.fetchUniqueCode(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(456, result.getRefCode()); 
        verify(uniqueCodeRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUniqueCodeNotFound() {
        int id = 1;

        when(uniqueCodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> uniqueCodeService.fetchUniqueCode(id));
        verify(uniqueCodeRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUniqueCode() {
        
    	UniqueCodeDto uniqueCodeDto = new UniqueCodeDto();
    	uniqueCodeDto.setId(1);
    	 uniqueCodeDto.setRefCode(456);

        UniqueCode existingUniqueCode = new UniqueCode();
        existingUniqueCode.setId(1); 
        existingUniqueCode.setRefCode(456);

        when(uniqueCodeRepository.findById(uniqueCodeDto.getId())).thenReturn(Optional.of(existingUniqueCode));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(uniqueCodeDto, existingUniqueCode))
                        .thenAnswer(invocation -> null);

            when(uniqueCodeRepository.save(existingUniqueCode)).thenReturn(existingUniqueCode);

            boolean result = uniqueCodeService.updateUniqueCode(uniqueCodeDto);

            assertTrue(result);
            verify(uniqueCodeRepository, times(1)).findById(uniqueCodeDto.getId());
            verify(uniqueCodeRepository, times(1)).save(existingUniqueCode);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(uniqueCodeDto, existingUniqueCode), times(1));
        }
    }


    @Test
    void testUpdateUniqueCodeNotFound() {
        UniqueCodeDto uniqueCodeDto = new UniqueCodeDto();
        uniqueCodeDto.setId(1);

        when(uniqueCodeRepository.findById(uniqueCodeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> uniqueCodeService.updateUniqueCode(uniqueCodeDto));
        verify(uniqueCodeRepository, times(1)).findById(uniqueCodeDto.getId());
    }

    @Test
    void testDeleteUniqueCode() {
        int id = 1;
        UniqueCode uniqueCode = new UniqueCode();
        uniqueCode.setId(id);

        when(uniqueCodeRepository.findById(id)).thenReturn(Optional.of(uniqueCode));
        doNothing().when(uniqueCodeRepository).deleteById(id);

        boolean result = uniqueCodeService.deleteUniqueCode(id);

        assertTrue(result);
        verify(uniqueCodeRepository, times(1)).findById(id);
        verify(uniqueCodeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUniqueCodeNotFound() {
        int id = 1;

        when(uniqueCodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> uniqueCodeService.deleteUniqueCode(id));
        verify(uniqueCodeRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUniqueCodes() {
        List<UniqueCode> uniqueCodeList = new ArrayList<>();
        uniqueCodeList.add(new UniqueCode());
        uniqueCodeList.add(new UniqueCode());

        when(uniqueCodeRepository.findAll()).thenReturn(uniqueCodeList);

        List<UniqueCode> result = uniqueCodeService.fetchAllUniqueCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(uniqueCodeRepository, times(1)).findAll();
    }

	
}
