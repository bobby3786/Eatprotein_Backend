package com.store.service.impl;

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

import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.dto.StoreTagDto;
import com.store.entity.StoreTag;
import com.store.repository.StoreTagRepository;


@ExtendWith(MockitoExtension.class)
public class StoreTagServiceImplTest {
	
	@Mock
    private StoreTagRepository storeTagRepository;

    @InjectMocks
    private StoreTagServiceImpl storeTagService;
    

    @Test
    void testCreateStoreTag() {
    	
    	 StoreTagDto storeTagDto = new StoreTagDto();
    	 storeTagDto.setStoreId(3);
		  storeTagDto.setTagId(2);
        
        
        StoreTag StoreTag = new StoreTag();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeTagDto, StoreTag))
                        .thenAnswer(invocation -> null);

            when(storeTagRepository.save(any(StoreTag.class))).thenReturn(StoreTag);

            storeTagService.createStoreTag(storeTagDto);

            verify(storeTagRepository, times(1)).save(any(StoreTag.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeTagDto, StoreTag), times(1));
        }
    }
    
    @Test
    void testCreateStoreTag_NullDto() {
       
        StoreTagDto storeTagDto = null;

        assertThrows(IllegalArgumentException.class, () -> storeTagService.createStoreTag(storeTagDto));
        verify(storeTagRepository, never()).save(any(StoreTag.class));
    }
    
    @Test
    void testFetchStoreTag() {
     
        String id = "1"; 

        StoreTag storeTag = new StoreTag();
        storeTag.setId(id);
        storeTag.setStoreId(3);
        storeTag.setTagId(2);


       when(storeTagRepository.findById(id)).thenReturn(Optional.of(storeTag));

        StoreTag result = storeTagService.fetchStoreTag(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(3, result.getStoreId()); 
        assertEquals(2, result.getTagId()); 
        verify(storeTagRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchStoreTagNotFound() {
        String id = "1";

        when(storeTagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeTagService.fetchStoreTag(id));
        verify(storeTagRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStoreTag() {
        
    	StoreTagDto storeTagDto = new StoreTagDto();
    	storeTagDto.setId("1");
    	storeTagDto.setStoreId(3);
    	storeTagDto.setTagId(2);
        
        
        StoreTag existingStoreTag = new StoreTag();
        existingStoreTag.setId("1"); 
        existingStoreTag.setStoreId(3);
        existingStoreTag.setTagId(2);

        when(storeTagRepository.findById(storeTagDto.getId())).thenReturn(Optional.of(existingStoreTag));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeTagDto, existingStoreTag))
                        .thenAnswer(invocation -> null);

            when(storeTagRepository.save(existingStoreTag)).thenReturn(existingStoreTag);

            boolean result = storeTagService.updateStoreTag(storeTagDto);

            assertTrue(result);
            verify(storeTagRepository, times(1)).findById(storeTagDto.getId());
            verify(storeTagRepository, times(1)).save(existingStoreTag);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeTagDto, existingStoreTag), times(1));
        }
    }


    @Test
    void testUpdateStoreTagNotFound() {
        StoreTagDto storeTagDto = new StoreTagDto();
        storeTagDto.setId("1");

        when(storeTagRepository.findById(storeTagDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeTagService.updateStoreTag(storeTagDto));
        verify(storeTagRepository, times(1)).findById(storeTagDto.getId());
    }

    @Test
    void testDeleteStoreTag() {
        String id = "1";
        StoreTag storeTag = new StoreTag();
        storeTag.setId(id);

        when(storeTagRepository.findById(id)).thenReturn(Optional.of(storeTag));
        doNothing().when(storeTagRepository).deleteById(id);

        boolean result = storeTagService.deleteStoreTag(id);

        assertTrue(result);
        verify(storeTagRepository, times(1)).findById(id);
        verify(storeTagRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStoreTagNotFound() {
        String id = "1";

        when(storeTagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeTagService.deleteStoreTag(id));
        verify(storeTagRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllStoreTags() {
        List<StoreTag> StoreTagList = new ArrayList<>();
        StoreTagList.add(new StoreTag());
        StoreTagList.add(new StoreTag());

        when(storeTagRepository.findAll()).thenReturn(StoreTagList);

        List<StoreTag> result = storeTagService.fetchAllStoreTags();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(storeTagRepository, times(1)).findAll();
    }


}
