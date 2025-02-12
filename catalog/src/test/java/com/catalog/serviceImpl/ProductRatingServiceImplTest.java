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

import com.catalog.dto.ProductRatingDto;
import com.catalog.entity.ProductRating;
import com.catalog.repository.ProductRatingRepository;
import com.catalog.service.impl.ProductRatingServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class ProductRatingServiceImplTest {
	
	@Mock
    private ProductRatingRepository productRatingRepository;

    @InjectMocks
    private ProductRatingServiceImpl productRatingService;
    

    @Test
    void testCreateProductRating() {
    	
    	 ProductRatingDto productRatingDto = new ProductRatingDto();
    	 productRatingDto.setRating(5);
        
        
        ProductRating productRating = new ProductRating();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productRatingDto, productRating))
                        .thenAnswer(invocation -> null);

            when(productRatingRepository.save(any(ProductRating.class))).thenReturn(productRating);

            productRatingService.createProductRating(productRatingDto);

            verify(productRatingRepository, times(1)).save(any(ProductRating.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productRatingDto, productRating), times(1));
        }
    }
    
    @Test
    void testCreateProductRating_NullDto() {
       
        ProductRatingDto productRatingDto = null;

        assertThrows(IllegalArgumentException.class, () -> productRatingService.createProductRating(productRatingDto));
        verify(productRatingRepository, never()).save(any(ProductRating.class));
    }
    
    @Test
    void testFetchProductRating() {
     
        int id = 1; 

        ProductRating productRating = new ProductRating();
        productRating.setId(id);
        productRating.setRating(5);


       when(productRatingRepository.findById(id)).thenReturn(Optional.of(productRating));

        ProductRating result = productRatingService.fetchProductRating(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(5, result.getRating());
        verify(productRatingRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchProductRatingNotFound() {
        int id = 1;

        when(productRatingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productRatingService.fetchProductRating(id));
        verify(productRatingRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateProductRating() {
        
    	ProductRatingDto productRatingDto = new ProductRatingDto();
    	productRatingDto.setId(1);
    	productRatingDto.setRating(5);

        ProductRating existingProductRating = new ProductRating();
        existingProductRating.setId(1); 
        existingProductRating.setRating(5);

        when(productRatingRepository.findById(productRatingDto.getId())).thenReturn(Optional.of(existingProductRating));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productRatingDto, existingProductRating))
                        .thenAnswer(invocation -> null);

            when(productRatingRepository.save(existingProductRating)).thenReturn(existingProductRating);

            boolean result = productRatingService.updateProductRating(productRatingDto);

            assertTrue(result);
            verify(productRatingRepository, times(1)).findById(productRatingDto.getId());
            verify(productRatingRepository, times(1)).save(existingProductRating);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productRatingDto, existingProductRating), times(1));
        }
    }


    @Test
    void testUpdateProductRatingNotFound() {
        ProductRatingDto productRatingDto = new ProductRatingDto();
        productRatingDto.setId(1);

        when(productRatingRepository.findById(productRatingDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productRatingService.updateProductRating(productRatingDto));
        verify(productRatingRepository, times(1)).findById(productRatingDto.getId());
    }

    @Test
    void testDeleteProductRating() {
        int id = 1;
        ProductRating productRating = new ProductRating();
        productRating.setId(id);

        when(productRatingRepository.findById(id)).thenReturn(Optional.of(productRating));
        doNothing().when(productRatingRepository).deleteById(id);

        boolean result = productRatingService.deleteProductRating(id);

        assertTrue(result);
        verify(productRatingRepository, times(1)).findById(id);
        verify(productRatingRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProductRatingNotFound() {
        int id = 1;

        when(productRatingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productRatingService.deleteProductRating(id));
        verify(productRatingRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllProductRatings() {
        List<ProductRating> productRatingList = new ArrayList<>();
        productRatingList.add(new ProductRating());
        productRatingList.add(new ProductRating());

        when(productRatingRepository.findAll()).thenReturn(productRatingList);

        List<ProductRating> result = productRatingService.fetchAllProductRatings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRatingRepository, times(1)).findAll();
    }


}
