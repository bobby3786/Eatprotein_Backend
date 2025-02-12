package com.catalog.serviceImpl;

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

import com.catalog.dto.UomDto;
import com.catalog.entity.Uom;
import com.catalog.repository.UomRepository;
import com.catalog.service.impl.UomServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class UomServiceImplTest {
	
	@Mock
    private UomRepository uomRepository;

    @InjectMocks
    private UomServiceImpl uomService;
    

    @Test
    void testCreateUom() {
    	
    	 UomDto uomDto = new UomDto();
    	 uomDto.setName("xyz");
        
        
        Uom Uom = new Uom();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(uomDto, Uom))
                        .thenAnswer(invocation -> null);

            when(uomRepository.save(any(Uom.class))).thenReturn(Uom);

            uomService.createUom(uomDto);

            verify(uomRepository, times(1)).save(any(Uom.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(uomDto, Uom), times(1));
        }
    }
    
    @Test
    void testCreateUom_NullDto() {
       
        UomDto uomDto = null;

        assertThrows(IllegalArgumentException.class, () -> uomService.createUom(uomDto));
        verify(uomRepository, never()).save(any(Uom.class));
    }
    
    @Test
    void testFetchUom() {
     
        int id = 1; 

        Uom uom = new Uom();
        uom.setId(id);
        uom.setName("xyz");


       when(uomRepository.findById(id)).thenReturn(Optional.of(uom));

        Uom result = uomService.fetchUom(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("xyz", result.getName()); 
        verify(uomRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUomNotFound() {
        int id = 1;

        when(uomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> uomService.fetchUom(id));
        verify(uomRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUom() {
        
    	UomDto uomDto = new UomDto();
    	uomDto.setId(1);
    	uomDto.setName("xyz");

        Uom existingUom = new Uom();
        existingUom.setId(1); 
        existingUom.setName("xyz");

        when(uomRepository.findById(uomDto.getId())).thenReturn(Optional.of(existingUom));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(uomDto, existingUom))
                        .thenAnswer(invocation -> null);

            when(uomRepository.save(existingUom)).thenReturn(existingUom);

            boolean result = uomService.updateUom(uomDto);

            assertTrue(result);
            verify(uomRepository, times(1)).findById(uomDto.getId());
            verify(uomRepository, times(1)).save(existingUom);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(uomDto, existingUom), times(1));
        }
    }


    @Test
    void testUpdateUomNotFound() {
        UomDto uomDto = new UomDto();
        uomDto.setId(1);

        when(uomRepository.findById(uomDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> uomService.updateUom(uomDto));
        verify(uomRepository, times(1)).findById(uomDto.getId());
    }

    @Test
    void testDeleteUom() {
        int id = 1;
        Uom uom = new Uom();
        uom.setId(id);

        when(uomRepository.findById(id)).thenReturn(Optional.of(uom));
        doNothing().when(uomRepository).deleteById(id);

        boolean result = uomService.deleteUom(id);

        assertTrue(result);
        verify(uomRepository, times(1)).findById(id);
        verify(uomRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUomNotFound() {
        int id = 1;

        when(uomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> uomService.deleteUom(id));
        verify(uomRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUoms() {
        List<Uom> uomList = new ArrayList<>();
        uomList.add(new Uom());
        uomList.add(new Uom());

        when(uomRepository.findAll()).thenReturn(uomList);

        List<Uom> result = uomService.fetchAllUoms();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(uomRepository, times(1)).findAll();
    }


}
