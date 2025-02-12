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

import com.payments.dto.PaymentUtilDto;
import com.payments.entity.PaymentUtil;
import com.payments.repository.PaymentUtilRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class PaymentUtilServiceImplTest {
	
	@Mock
    private PaymentUtilRepository paymentUtilRepository;

    @InjectMocks
    private PaymentUtilServiceImpl paymentUtilService;
    

    @Test
    void testCreatePaymentUtil() {
    	
    	 PaymentUtilDto paymentUtilDto = new PaymentUtilDto();
    	 paymentUtilDto.setAmount(500);
		  paymentUtilDto.setStoreId(12);
        
        
        PaymentUtil paymentUtil = new PaymentUtil();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentUtilDto, paymentUtil))
                        .thenAnswer(invocation -> null);

            when(paymentUtilRepository.save(any(PaymentUtil.class))).thenReturn(paymentUtil);

            paymentUtilService.createPaymentUtil(paymentUtilDto);

            verify(paymentUtilRepository, times(1)).save(any(PaymentUtil.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentUtilDto, paymentUtil), times(1));
        }
    }
    
    @Test
    void testCreatePaymentUtil_NullDto() {
       
        PaymentUtilDto paymentUtilDto = null;

        assertThrows(IllegalArgumentException.class, () -> paymentUtilService.createPaymentUtil(paymentUtilDto));
        verify(paymentUtilRepository, never()).save(any(PaymentUtil.class));
    }
    
    @Test
    void testFetchPaymentUtil() {
     
        int id = 1; 

        PaymentUtil paymentUtil = new PaymentUtil();
        paymentUtil.setId(id);
        paymentUtil.setAmount(500);
        paymentUtil.setStoreId(12);


       when(paymentUtilRepository.findById(id)).thenReturn(Optional.of(paymentUtil));

        PaymentUtil result = paymentUtilService.fetchPaymentUtil(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(500, result.getAmount()); 
        assertEquals(12, result.getStoreId()); 
        verify(paymentUtilRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchPaymentUtilNotFound() {
        int id = 1;

        when(paymentUtilRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentUtilService.fetchPaymentUtil(id));
        verify(paymentUtilRepository, times(1)).findById(id);
    }

    @Test
    void testUpdatePaymentUtil() {
        
    	PaymentUtilDto paymentUtilDto = new PaymentUtilDto();
    	paymentUtilDto.setId(1);
    	 paymentUtilDto.setAmount(500);
		  paymentUtilDto.setStoreId(12);

        PaymentUtil existingPaymentUtil = new PaymentUtil();
        existingPaymentUtil.setId(1); 
        existingPaymentUtil.setAmount(500);
        existingPaymentUtil.setStoreId(12);

        when(paymentUtilRepository.findById(paymentUtilDto.getId())).thenReturn(Optional.of(existingPaymentUtil));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentUtilDto, existingPaymentUtil))
                        .thenAnswer(invocation -> null);

            when(paymentUtilRepository.save(existingPaymentUtil)).thenReturn(existingPaymentUtil);

            boolean result = paymentUtilService.updatePaymentUtil(paymentUtilDto);

            assertTrue(result);
            verify(paymentUtilRepository, times(1)).findById(paymentUtilDto.getId());
            verify(paymentUtilRepository, times(1)).save(existingPaymentUtil);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentUtilDto, existingPaymentUtil), times(1));
        }
    }


    @Test
    void testUpdatePaymentUtilNotFound() {
        PaymentUtilDto paymentUtilDto = new PaymentUtilDto();
        paymentUtilDto.setId(1);

        when(paymentUtilRepository.findById(paymentUtilDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentUtilService.updatePaymentUtil(paymentUtilDto));
        verify(paymentUtilRepository, times(1)).findById(paymentUtilDto.getId());
    }

    @Test
    void testDeletePaymentUtil() {
        int id = 1;
        PaymentUtil paymentUtil = new PaymentUtil();
        paymentUtil.setId(id);

        when(paymentUtilRepository.findById(id)).thenReturn(Optional.of(paymentUtil));
        doNothing().when(paymentUtilRepository).deleteById(id);

        boolean result = paymentUtilService.deletePaymentUtil(id);

        assertTrue(result);
        verify(paymentUtilRepository, times(1)).findById(id);
        verify(paymentUtilRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePaymentUtilNotFound() {
        int id = 1;

        when(paymentUtilRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentUtilService.deletePaymentUtil(id));
        verify(paymentUtilRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllPaymentUtils() {
        List<PaymentUtil> paymentUtilList = new ArrayList<>();
        paymentUtilList.add(new PaymentUtil());
        paymentUtilList.add(new PaymentUtil());

        when(paymentUtilRepository.findAll()).thenReturn(paymentUtilList);

        List<PaymentUtil> result = paymentUtilService.fetchAllPaymentUtils();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentUtilRepository, times(1)).findAll();
    }



}
