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
import com.store.dto.ProductPriceDto;
import com.store.entity.ProductPrice;
import com.store.repository.ProductPriceRepository;


@ExtendWith(MockitoExtension.class)
public class ProductPriceServiceImplTest {
	
	@Mock
    private ProductPriceRepository productPriceRepository;

    @InjectMocks
    private ProductPriceServiceImpl productPriceService;
    

    @Test
    void testCreateProductPrice() {
    	
    	 ProductPriceDto productPriceDto = new ProductPriceDto();
    	 productPriceDto.setStatus("Active");
        
        
        ProductPrice productPrice = new ProductPrice();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productPriceDto, productPrice))
                        .thenAnswer(invocation -> null);

            when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(productPrice);

            productPriceService.createProductPrice(productPriceDto);

            verify(productPriceRepository, times(1)).save(any(ProductPrice.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productPriceDto, productPrice), times(1));
        }
    }
    
    @Test
    void testCreateProductPrice_NullDto() {
       
        ProductPriceDto productPriceDto = null;

        assertThrows(IllegalArgumentException.class, () -> productPriceService.createProductPrice(productPriceDto));
        verify(productPriceRepository, never()).save(any(ProductPrice.class));
    }
    
    @Test
    void testFetchProductPrice() {
     
    	String id = "1"; 

        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(id);
        productPrice.setStatus("Active");


       when(productPriceRepository.findById(id)).thenReturn(Optional.of(productPrice));

        ProductPrice result = productPriceService.fetchProductPrice(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("Active", result.getStatus());
        verify(productPriceRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchProductPriceNotFound() {
    	String id = "1";

        when(productPriceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productPriceService.fetchProductPrice(id));
        verify(productPriceRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateProductPrice() {
        
    	ProductPriceDto productPriceDto = new ProductPriceDto();
    	productPriceDto.setId("1");
    	productPriceDto.setStatus("Active");

        ProductPrice existingProductPrice = new ProductPrice();
        existingProductPrice.setId("1"); 
        existingProductPrice.setStatus("Active");

        when(productPriceRepository.findById(productPriceDto.getId())).thenReturn(Optional.of(existingProductPrice));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productPriceDto, existingProductPrice))
                        .thenAnswer(invocation -> null);

            when(productPriceRepository.save(existingProductPrice)).thenReturn(existingProductPrice);

            boolean result = productPriceService.updateProductPrice(productPriceDto);

            assertTrue(result);
            verify(productPriceRepository, times(1)).findById(productPriceDto.getId());
            verify(productPriceRepository, times(1)).save(existingProductPrice);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productPriceDto, existingProductPrice), times(1));
        }
    }


    @Test
    void testUpdateProductPriceNotFound() {
        ProductPriceDto productPriceDto = new ProductPriceDto();
        productPriceDto.setId("1");

        when(productPriceRepository.findById(productPriceDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productPriceService.updateProductPrice(productPriceDto));
        verify(productPriceRepository, times(1)).findById(productPriceDto.getId());
    }

    @Test
    void testDeleteProductPrice() {
    	String id = "1";
        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(id);

        when(productPriceRepository.findById(id)).thenReturn(Optional.of(productPrice));
        doNothing().when(productPriceRepository).deleteById(id);

        boolean result = productPriceService.deleteProductPrice(id);

        assertTrue(result);
        verify(productPriceRepository, times(1)).findById(id);
        verify(productPriceRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProductPriceNotFound() {
    	String id = "1";

        when(productPriceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productPriceService.deleteProductPrice(id));
        verify(productPriceRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllProductPrices() {
        List<ProductPrice> productPriceList = new ArrayList<>();
        productPriceList.add(new ProductPrice());
        productPriceList.add(new ProductPrice());

        when(productPriceRepository.findAll()).thenReturn(productPriceList);

        List<ProductPrice> result = productPriceService.fetchAllProductPrices();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productPriceRepository, times(1)).findAll();
    }


}
