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

import com.authentication.dto.WalletTransactionDto;
import com.authentication.entity.WalletTransaction;
import com.authentication.repository.WalletTransactionRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class WalletTransactionServiceImplTest {
	
	@Mock
    private WalletTransactionRepository walletTransactionRepository;

    @InjectMocks
    private WalletTransactionServiceImpl walletTransactionService;
    

    @Test
    void testCreateWalletTransaction() {
    	
    	 WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
    	 walletTransactionDto.setAmount("1000");
		  walletTransactionDto.setReason("Bad Quality");
        
        
        WalletTransaction walletTransaction = new WalletTransaction();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(walletTransactionDto, walletTransaction))
                        .thenAnswer(invocation -> null);

            when(walletTransactionRepository.save(any(WalletTransaction.class))).thenReturn(walletTransaction);

            walletTransactionService.createWalletTransaction(walletTransactionDto);

            verify(walletTransactionRepository, times(1)).save(any(WalletTransaction.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(walletTransactionDto, walletTransaction), times(1));
        }
    }
    
    @Test
    void testCreateWalletTransaction_NullDto() {
       
        WalletTransactionDto walletTransactionDto = null;

        assertThrows(IllegalArgumentException.class, () -> walletTransactionService.createWalletTransaction(walletTransactionDto));
        verify(walletTransactionRepository, never()).save(any(WalletTransaction.class));
    }
    
    @Test
    void testFetchWalletTransaction() {
     
        int id = 1; 

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setId(id);
        walletTransaction.setAmount("1000");
        walletTransaction.setReason("Bad Quality");


       when(walletTransactionRepository.findById(id)).thenReturn(Optional.of(walletTransaction));

        WalletTransaction result = walletTransactionService.fetchWalletTransaction(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("1000", result.getAmount()); 
        assertEquals("Bad Quality", result.getReason()); 
        verify(walletTransactionRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchWalletTransactionNotFound() {
        int id = 1;

        when(walletTransactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> walletTransactionService.fetchWalletTransaction(id));
        verify(walletTransactionRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateWalletTransaction() {
        
    	WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
    	walletTransactionDto.setId(1);
    	walletTransactionDto.setAmount("1000");
		  walletTransactionDto.setReason("Bad Quality");

        WalletTransaction existingWalletTransaction = new WalletTransaction();
        existingWalletTransaction.setId(1); 
        existingWalletTransaction.setAmount("1000");
        existingWalletTransaction.setReason("Bad Quality");

        when(walletTransactionRepository.findById(walletTransactionDto.getId())).thenReturn(Optional.of(existingWalletTransaction));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(walletTransactionDto, existingWalletTransaction))
                        .thenAnswer(invocation -> null);

            when(walletTransactionRepository.save(existingWalletTransaction)).thenReturn(existingWalletTransaction);

            boolean result = walletTransactionService.updateWalletTransaction(walletTransactionDto);

            assertTrue(result);
            verify(walletTransactionRepository, times(1)).findById(walletTransactionDto.getId());
            verify(walletTransactionRepository, times(1)).save(existingWalletTransaction);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(walletTransactionDto, existingWalletTransaction), times(1));
        }
    }


    @Test
    void testUpdateWalletTransactionNotFound() {
        WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
        walletTransactionDto.setId(1);

        when(walletTransactionRepository.findById(walletTransactionDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> walletTransactionService.updateWalletTransaction(walletTransactionDto));
        verify(walletTransactionRepository, times(1)).findById(walletTransactionDto.getId());
    }

    @Test
    void testDeleteWalletTransaction() {
        int id = 1;
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setId(id);

        when(walletTransactionRepository.findById(id)).thenReturn(Optional.of(walletTransaction));
        doNothing().when(walletTransactionRepository).deleteById(id);

        boolean result = walletTransactionService.deleteWalletTransaction(id);

        assertTrue(result);
        verify(walletTransactionRepository, times(1)).findById(id);
        verify(walletTransactionRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteWalletTransactionNotFound() {
        int id = 1;

        when(walletTransactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> walletTransactionService.deleteWalletTransaction(id));
        verify(walletTransactionRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllWalletTransactions() {
        List<WalletTransaction> walletTransactionList = new ArrayList<>();
        walletTransactionList.add(new WalletTransaction());
        walletTransactionList.add(new WalletTransaction());

        when(walletTransactionRepository.findAll()).thenReturn(walletTransactionList);

        List<WalletTransaction> result = walletTransactionService.fetchAllWalletTransactions();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(walletTransactionRepository, times(1)).findAll();
    }


}
