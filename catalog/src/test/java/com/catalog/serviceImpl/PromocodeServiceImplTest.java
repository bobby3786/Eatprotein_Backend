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

import com.catalog.dto.PromocodeDto;
import com.catalog.entity.Promocode;
import com.catalog.repository.PromocodeRepository;
import com.catalog.service.impl.PromocodeServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class PromocodeServiceImplTest {
	
	@Mock
    private PromocodeRepository promocodeRepository;

    @InjectMocks
    private PromocodeServiceImpl promocodeService;
    

    @Test
    void testCreatePromocode() {
    	
    	 PromocodeDto promocodeDto = new PromocodeDto();
    	 promocodeDto.setImagePath("Promocode/image.jpg");
        
        
        Promocode promocode = new Promocode();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(promocodeDto, promocode))
                        .thenAnswer(invocation -> null);

            when(promocodeRepository.save(any(Promocode.class))).thenReturn(promocode);

            promocodeService.createPromocode(promocodeDto);

            verify(promocodeRepository, times(1)).save(any(Promocode.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(promocodeDto, promocode), times(1));
        }
    }
    
    @Test
    void testCreatePromocode_NullDto() {
       
        PromocodeDto promocodeDto = null;

        assertThrows(IllegalArgumentException.class, () -> promocodeService.createPromocode(promocodeDto));
        verify(promocodeRepository, never()).save(any(Promocode.class));
    }
    
    @Test
    void testFetchPromocode() {
     
        int id = 1; 

        Promocode promocode = new Promocode();
        promocode.setId(id);
        promocode.setImagePath("Promocode/image.jpg");


       when(promocodeRepository.findById(id)).thenReturn(Optional.of(promocode));

        Promocode result = promocodeService.fetchPromocode(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("Promocode/image.jpg", result.getImagePath()); 
        verify(promocodeRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchPromocodeNotFound() {
        int id = 1;

        when(promocodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> promocodeService.fetchPromocode(id));
        verify(promocodeRepository, times(1)).findById(id);
    }

    @Test
    void testUpdatePromocode() {
        
    	PromocodeDto promocodeDto = new PromocodeDto();
    	promocodeDto.setId(1);
    	promocodeDto.setImagePath("Promocode/image.jpg");

        Promocode existingPromocode = new Promocode();
        existingPromocode.setId(1); 
        existingPromocode.setImagePath("Promocode/image.jpg");

        when(promocodeRepository.findById(promocodeDto.getId())).thenReturn(Optional.of(existingPromocode));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(promocodeDto, existingPromocode))
                        .thenAnswer(invocation -> null);

            when(promocodeRepository.save(existingPromocode)).thenReturn(existingPromocode);

            boolean result = promocodeService.updatePromocode(promocodeDto);

            assertTrue(result);
            verify(promocodeRepository, times(1)).findById(promocodeDto.getId());
            verify(promocodeRepository, times(1)).save(existingPromocode);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(promocodeDto, existingPromocode), times(1));
        }
    }


    @Test
    void testUpdatePromocodeNotFound() {
        PromocodeDto promocodeDto = new PromocodeDto();
        promocodeDto.setId(1);

        when(promocodeRepository.findById(promocodeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> promocodeService.updatePromocode(promocodeDto));
        verify(promocodeRepository, times(1)).findById(promocodeDto.getId());
    }

    @Test
    void testDeletePromocode() {
        int id = 1;
        Promocode promocode = new Promocode();
        promocode.setId(id);

        when(promocodeRepository.findById(id)).thenReturn(Optional.of(promocode));
        doNothing().when(promocodeRepository).deleteById(id);

        boolean result = promocodeService.deletePromocode(id);

        assertTrue(result);
        verify(promocodeRepository, times(1)).findById(id);
        verify(promocodeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePromocodeNotFound() {
        int id = 1;

        when(promocodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> promocodeService.deletePromocode(id));
        verify(promocodeRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllPromocodes() {
        List<Promocode> promocodeList = new ArrayList<>();
        promocodeList.add(new Promocode());
        promocodeList.add(new Promocode());

        when(promocodeRepository.findAll()).thenReturn(promocodeList);

        List<Promocode> result = promocodeService.fetchAllPromocodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(promocodeRepository, times(1)).findAll();
    }



}
