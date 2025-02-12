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

import com.payments.dto.PaymentDbDto;
import com.payments.entity.PaymentDb;
import com.payments.repository.PaymentDbRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class PaymentDbServiceImplTest {
	
	@Mock
    private PaymentDbRepository paymentDbRepository;

    @InjectMocks
    private PaymentDbServiceImpl paymentDbService;
    

    @Test
    void testCreatePaymentDb() {
    	
    	 PaymentDbDto paymentDbDto = new PaymentDbDto();
    	  paymentDbDto.setDeliveryCharges(32.50);
		  paymentDbDto.setOrdersCount(34);
        
        
        PaymentDb paymentDb = new PaymentDb();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDbDto, paymentDb))
                        .thenAnswer(invocation -> null);

            when(paymentDbRepository.save(any(PaymentDb.class))).thenReturn(paymentDb);

            paymentDbService.createPaymentDb(paymentDbDto);

            verify(paymentDbRepository, times(1)).save(any(PaymentDb.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDbDto, paymentDb), times(1));
        }
    }
    
    @Test
    void testCreatePaymentDb_NullDto() {
       
        PaymentDbDto paymentDbDto = null;

        assertThrows(IllegalArgumentException.class, () -> paymentDbService.createPaymentDb(paymentDbDto));
        verify(paymentDbRepository, never()).save(any(PaymentDb.class));
    }
    
    @Test
    void testFetchPaymentDb() {
     
        int id = 1; 

        PaymentDb paymentDb = new PaymentDb();
        paymentDb.setId(id);
        paymentDb.setDeliveryCharges(32.50);
        paymentDb.setOrdersCount(34);


       when(paymentDbRepository.findById(id)).thenReturn(Optional.of(paymentDb));

        PaymentDb result = paymentDbService.fetchPaymentDb(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(32.50, result.getDeliveryCharges()); 
        assertEquals(34, result.getOrdersCount()); 
        verify(paymentDbRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchPaymentDbNotFound() {
        int id = 1;

        when(paymentDbRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentDbService.fetchPaymentDb(id));
        verify(paymentDbRepository, times(1)).findById(id);
    }

    @Test
    void testUpdatePaymentDb() {
        
    	PaymentDbDto paymentDbDto = new PaymentDbDto();
    	paymentDbDto.setId(1);
    	  paymentDbDto.setDeliveryCharges(32.50);
		  paymentDbDto.setOrdersCount(34);

        PaymentDb existingPaymentDb = new PaymentDb();
        existingPaymentDb.setId(1); 
        existingPaymentDb.setDeliveryCharges(32.50);
        existingPaymentDb.setOrdersCount(34);

        when(paymentDbRepository.findById(paymentDbDto.getId())).thenReturn(Optional.of(existingPaymentDb));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDbDto, existingPaymentDb))
                        .thenAnswer(invocation -> null);

            when(paymentDbRepository.save(existingPaymentDb)).thenReturn(existingPaymentDb);

            boolean result = paymentDbService.updatePaymentDb(paymentDbDto);

            assertTrue(result);
            verify(paymentDbRepository, times(1)).findById(paymentDbDto.getId());
            verify(paymentDbRepository, times(1)).save(existingPaymentDb);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDbDto, existingPaymentDb), times(1));
        }
    }


    @Test
    void testUpdatePaymentDbNotFound() {
        PaymentDbDto paymentDbDto = new PaymentDbDto();
        paymentDbDto.setId(1);

        when(paymentDbRepository.findById(paymentDbDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentDbService.updatePaymentDb(paymentDbDto));
        verify(paymentDbRepository, times(1)).findById(paymentDbDto.getId());
    }

    @Test
    void testDeletePaymentDb() {
        int id = 1;
        PaymentDb paymentDb = new PaymentDb();
        paymentDb.setId(id);

        when(paymentDbRepository.findById(id)).thenReturn(Optional.of(paymentDb));
        doNothing().when(paymentDbRepository).deleteById(id);

        boolean result = paymentDbService.deletePaymentDb(id);

        assertTrue(result);
        verify(paymentDbRepository, times(1)).findById(id);
        verify(paymentDbRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePaymentDbNotFound() {
        int id = 1;

        when(paymentDbRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentDbService.deletePaymentDb(id));
        verify(paymentDbRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllPaymentDbs() {
        List<PaymentDb> paymentDbList = new ArrayList<>();
        paymentDbList.add(new PaymentDb());
        paymentDbList.add(new PaymentDb());

        when(paymentDbRepository.findAll()).thenReturn(paymentDbList);

        List<PaymentDb> result = paymentDbService.fetchAllPaymentDbs();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentDbRepository, times(1)).findAll();
    }


}
