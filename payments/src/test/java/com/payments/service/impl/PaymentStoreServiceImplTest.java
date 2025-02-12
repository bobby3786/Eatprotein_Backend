package com.payments.service.impl;

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

import com.payments.dto.PaymentStoreDto;
import com.payments.entity.PaymentStore;
import com.payments.repository.PaymentStoreRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class PaymentStoreServiceImplTest {
	
	@Mock
    private PaymentStoreRepository paymentStoreRepository;

    @InjectMocks
    private PaymentStoreServiceImpl paymentStoreService;
    

    @Test
    void testCreatePaymentStore() {
    	
    	 PaymentStoreDto paymentStoreDto = new PaymentStoreDto();
    	 paymentStoreDto.setDeliveryCharges(32.50);
		  paymentStoreDto.setPaymentStatus("completed");
        
        
        PaymentStore paymentStore = new PaymentStore();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentStoreDto, paymentStore))
                        .thenAnswer(invocation -> null);

            when(paymentStoreRepository.save(any(PaymentStore.class))).thenReturn(paymentStore);

            paymentStoreService.createPaymentStore(paymentStoreDto);

            verify(paymentStoreRepository, times(1)).save(any(PaymentStore.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentStoreDto, paymentStore), times(1));
        }
    }
    
    @Test
    void testCreatePaymentStore_NullDto() {
       
        PaymentStoreDto paymentStoreDto = null;

        assertThrows(IllegalArgumentException.class, () -> paymentStoreService.createPaymentStore(paymentStoreDto));
        verify(paymentStoreRepository, never()).save(any(PaymentStore.class));
    }
    
    @Test
    void testFetchPaymentStore() {
     
        int id = 1; 

        PaymentStore paymentStore = new PaymentStore();
        paymentStore.setId(id);
        paymentStore.setDeliveryCharges(32.50);
        paymentStore.setPaymentStatus("completed");


       when(paymentStoreRepository.findById(id)).thenReturn(Optional.of(paymentStore));

        PaymentStore result = paymentStoreService.fetchPaymentStore(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(32.50, result.getDeliveryCharges()); 
        assertEquals("completed", result.getPaymentStatus()); 
        verify(paymentStoreRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchPaymentStoreNotFound() {
        int id = 1;

        when(paymentStoreRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentStoreService.fetchPaymentStore(id));
        verify(paymentStoreRepository, times(1)).findById(id);
    }

    @Test
    void testUpdatePaymentStore() {
        
    	PaymentStoreDto paymentStoreDto = new PaymentStoreDto();
    	paymentStoreDto.setId(1);
    	 paymentStoreDto.setDeliveryCharges(32.50);
		  paymentStoreDto.setPaymentStatus("completed");

        PaymentStore existingPaymentStore = new PaymentStore();
        existingPaymentStore.setId(1); 
        existingPaymentStore.setDeliveryCharges(32.50);
        existingPaymentStore.setPaymentStatus("completed");

        when(paymentStoreRepository.findById(paymentStoreDto.getId())).thenReturn(Optional.of(existingPaymentStore));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentStoreDto, existingPaymentStore))
                        .thenAnswer(invocation -> null);

            when(paymentStoreRepository.save(existingPaymentStore)).thenReturn(existingPaymentStore);

            boolean result = paymentStoreService.updatePaymentStore(paymentStoreDto);

            assertTrue(result);
            verify(paymentStoreRepository, times(1)).findById(paymentStoreDto.getId());
            verify(paymentStoreRepository, times(1)).save(existingPaymentStore);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentStoreDto, existingPaymentStore), times(1));
        }
    }


    @Test
    void testUpdatePaymentStoreNotFound() {
        PaymentStoreDto paymentStoreDto = new PaymentStoreDto();
        paymentStoreDto.setId(1);

        when(paymentStoreRepository.findById(paymentStoreDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentStoreService.updatePaymentStore(paymentStoreDto));
        verify(paymentStoreRepository, times(1)).findById(paymentStoreDto.getId());
    }

    @Test
    void testDeletePaymentStore() {
        int id = 1;
        PaymentStore paymentStore = new PaymentStore();
        paymentStore.setId(id);

        when(paymentStoreRepository.findById(id)).thenReturn(Optional.of(paymentStore));
        doNothing().when(paymentStoreRepository).deleteById(id);

        boolean result = paymentStoreService.deletePaymentStore(id);

        assertTrue(result);
        verify(paymentStoreRepository, times(1)).findById(id);
        verify(paymentStoreRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePaymentStoreNotFound() {
        int id = 1;

        when(paymentStoreRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentStoreService.deletePaymentStore(id));
        verify(paymentStoreRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllPaymentStores() {
        List<PaymentStore> paymentStoreList = new ArrayList<>();
        paymentStoreList.add(new PaymentStore());
        paymentStoreList.add(new PaymentStore());

        when(paymentStoreRepository.findAll()).thenReturn(paymentStoreList);

        List<PaymentStore> result = paymentStoreService.fetchAllPaymentStores();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentStoreRepository, times(1)).findAll();
    }


}
