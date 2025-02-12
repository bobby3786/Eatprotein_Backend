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
import com.store.dto.StoreOrderDto;
import com.store.entity.StoreOrder;
import com.store.repository.StoreOrderRepository;


@ExtendWith(MockitoExtension.class)
public class StoreOrderServiceImplTest {
	
	@Mock
    private StoreOrderRepository storeOrderRepository;

    @InjectMocks
    private StoreOrderServiceImpl storeOrderService;
    

    @Test
    void testCreateStoreOrder() {
    	
    	 StoreOrderDto storeOrderDto = new StoreOrderDto();
    	 storeOrderDto.setTotalPrice(5000);
		  storeOrderDto.setStoreId(1);
        
        
        StoreOrder storeOrder = new StoreOrder();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeOrderDto, storeOrder))
                        .thenAnswer(invocation -> null);

            when(storeOrderRepository.save(any(StoreOrder.class))).thenReturn(storeOrder);

            storeOrderService.createStoreOrder(storeOrderDto);

            verify(storeOrderRepository, times(1)).save(any(StoreOrder.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeOrderDto, storeOrder), times(1));
        }
    }
    
    @Test
    void testCreateStoreOrder_NullDto() {
       
        StoreOrderDto storeOrderDto = null;

        assertThrows(IllegalArgumentException.class, () -> storeOrderService.createStoreOrder(storeOrderDto));
        verify(storeOrderRepository, never()).save(any(StoreOrder.class));
    }
    
    @Test
    void testFetchStoreOrder() {
     
        String id = "1"; 

        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(id);
        storeOrder.setTotalPrice(5000);
        storeOrder.setStoreId(1);


       when(storeOrderRepository.findById(id)).thenReturn(Optional.of(storeOrder));

        StoreOrder result = storeOrderService.fetchStoreOrder(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(5000, result.getTotalPrice()); 
        assertEquals(1, result.getStoreId()); 
        verify(storeOrderRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchStoreOrderNotFound() {
        String id = "1";

        when(storeOrderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeOrderService.fetchStoreOrder(id));
        verify(storeOrderRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStoreOrder() {
        
    	StoreOrderDto storeOrderDto = new StoreOrderDto();
    	storeOrderDto.setId("1");
    	storeOrderDto.setTotalPrice(5000);
		  storeOrderDto.setStoreId(1);

        StoreOrder existingStoreOrder = new StoreOrder();
        existingStoreOrder.setId("1"); 
        existingStoreOrder.setTotalPrice(5000);
        existingStoreOrder.setStoreId(1);

        when(storeOrderRepository.findById(storeOrderDto.getId())).thenReturn(Optional.of(existingStoreOrder));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeOrderDto, existingStoreOrder))
                        .thenAnswer(invocation -> null);

            when(storeOrderRepository.save(existingStoreOrder)).thenReturn(existingStoreOrder);

            boolean result = storeOrderService.updateStoreOrder(storeOrderDto);

            assertTrue(result);
            verify(storeOrderRepository, times(1)).findById(storeOrderDto.getId());
            verify(storeOrderRepository, times(1)).save(existingStoreOrder);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeOrderDto, existingStoreOrder), times(1));
        }
    }


    @Test
    void testUpdateStoreOrderNotFound() {
        StoreOrderDto storeOrderDto = new StoreOrderDto();
        storeOrderDto.setId("1");

        when(storeOrderRepository.findById(storeOrderDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeOrderService.updateStoreOrder(storeOrderDto));
        verify(storeOrderRepository, times(1)).findById(storeOrderDto.getId());
    }

    @Test
    void testDeleteStoreOrder() {
        String id = "1";
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(id);

        when(storeOrderRepository.findById(id)).thenReturn(Optional.of(storeOrder));
        doNothing().when(storeOrderRepository).deleteById(id);

        boolean result = storeOrderService.deleteStoreOrder(id);

        assertTrue(result);
        verify(storeOrderRepository, times(1)).findById(id);
        verify(storeOrderRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStoreOrderNotFound() {
        String id = "1";

        when(storeOrderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeOrderService.deleteStoreOrder(id));
        verify(storeOrderRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllStoreOrders() {
        List<StoreOrder> storeOrderList = new ArrayList<>();
        storeOrderList.add(new StoreOrder());
        storeOrderList.add(new StoreOrder());

        when(storeOrderRepository.findAll()).thenReturn(storeOrderList);

        List<StoreOrder> result = storeOrderService.fetchAllStoreOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(storeOrderRepository, times(1)).findAll();
    }

}
