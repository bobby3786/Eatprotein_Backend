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
import com.store.dto.StoreDto;
import com.store.entity.Store;
import com.store.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
public class StoreServiceImplTest {
	
	@Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl storeService;
    

    @Test
    void testCreateStore() {
    	
    	 StoreDto storeDto = new StoreDto();
    	 storeDto.setStatus("active");
		  storeDto.setCategoryId(1);
        
        
        Store store = new Store();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeDto, store))
                        .thenAnswer(invocation -> null);

            when(storeRepository.save(any(Store.class))).thenReturn(store);

            storeService.createStore(storeDto);

            verify(storeRepository, times(1)).save(any(Store.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeDto, store), times(1));
        }
    }
    
    @Test
    void testCreateStore_NullDto() {
       
        StoreDto storeDto = null;

        assertThrows(IllegalArgumentException.class, () -> storeService.createStore(storeDto));
        verify(storeRepository, never()).save(any(Store.class));
    }
    
    @Test
    void testFetchStore() {
     
        String id = "1"; 

        Store store = new Store();
        store.setId(id);
        store.setStatus("active");
        store.setCategoryId(1);


       when(storeRepository.findById(id)).thenReturn(Optional.of(store));

        Store result = storeService.fetchStore(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("active", result.getStatus()); 
        assertEquals(1, result.getCategoryId()); 
        verify(storeRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchStoreNotFound() {
        String id = "1";

        when(storeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeService.fetchStore(id));
        verify(storeRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStore() {
        
    	StoreDto storeDto = new StoreDto();
    	storeDto.setId("1");
    	storeDto.setStatus("active");
		  storeDto.setCategoryId(1);

        Store existingStore = new Store();
        existingStore.setId("1"); 
        existingStore.setStatus("active");
        existingStore.setCategoryId(1);

        when(storeRepository.findById(storeDto.getId())).thenReturn(Optional.of(existingStore));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeDto, existingStore))
                        .thenAnswer(invocation -> null);

            when(storeRepository.save(existingStore)).thenReturn(existingStore);

            boolean result = storeService.updateStore(storeDto);

            assertTrue(result);
            verify(storeRepository, times(1)).findById(storeDto.getId());
            verify(storeRepository, times(1)).save(existingStore);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeDto, existingStore), times(1));
        }
    }


    @Test
    void testUpdateStoreNotFound() {
        StoreDto storeDto = new StoreDto();
        storeDto.setId("1");

        when(storeRepository.findById(storeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeService.updateStore(storeDto));
        verify(storeRepository, times(1)).findById(storeDto.getId());
    }

    @Test
    void testDeleteStore() {
        String id = "1";
        Store store = new Store();
        store.setId(id);

        when(storeRepository.findById(id)).thenReturn(Optional.of(store));
        doNothing().when(storeRepository).deleteById(id);

        boolean result = storeService.deleteStore(id);

        assertTrue(result);
        verify(storeRepository, times(1)).findById(id);
        verify(storeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStoreNotFound() {
        String id = "1";

        when(storeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeService.deleteStore(id));
        verify(storeRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllStores() {
        List<Store> storeList = new ArrayList<>();
        storeList.add(new Store());
        storeList.add(new Store());

        when(storeRepository.findAll()).thenReturn(storeList);

        List<Store> result = storeService.fetchAllStores();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(storeRepository, times(1)).findAll();
    }


}
