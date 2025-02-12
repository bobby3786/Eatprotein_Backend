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

import com.catalog.dto.ProductImageDto;
import com.catalog.entity.ProductImage;
import com.catalog.repository.ProductImageRepository;
import com.catalog.service.impl.ProductImageServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class ProductImageServiceImplTest {
	
	@Mock
    private ProductImageRepository productImageRepository;

    @InjectMocks
    private ProductImageServiceImpl productImageService;
    

    @Test
    void testCreateProductImage() {
    	
    	 ProductImageDto productImageDto = new ProductImageDto();
    	 productImageDto.setImagePath("product/image.jpg");
        
        
        ProductImage productImage = new ProductImage();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productImageDto, productImage))
                        .thenAnswer(invocation -> null);

            when(productImageRepository.save(any(ProductImage.class))).thenReturn(productImage);

            productImageService.createProductImage(productImageDto);

            verify(productImageRepository, times(1)).save(any(ProductImage.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productImageDto, productImage), times(1));
        }
    }
    
    @Test
    void testCreateProductImage_NullDto() {
       
        ProductImageDto productImageDto = null;

        assertThrows(IllegalArgumentException.class, () -> productImageService.createProductImage(productImageDto));
        verify(productImageRepository, never()).save(any(ProductImage.class));
    }
    
    @Test
    void testFetchProductImage() {
     
        int id = 1; 

        ProductImage productImage = new ProductImage();
        productImage.setId(id);
        productImage.setImagePath("product/image.jpg");


       when(productImageRepository.findById(id)).thenReturn(Optional.of(productImage));

        ProductImage result = productImageService.fetchProductImage(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("product/image.jpg", result.getImagePath());
        verify(productImageRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchProductImageNotFound() {
        int id = 1;

        when(productImageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productImageService.fetchProductImage(id));
        verify(productImageRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateProductImage() {
        
    	ProductImageDto productImageDto = new ProductImageDto();
    	productImageDto.setId(1);
    	productImageDto.setImagePath("product/image.jpg");

        ProductImage existingProductImage = new ProductImage();
        existingProductImage.setId(1); 
        productImageDto.setImagePath("product/image.jpg");

        when(productImageRepository.findById(productImageDto.getId())).thenReturn(Optional.of(existingProductImage));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productImageDto, existingProductImage))
                        .thenAnswer(invocation -> null);

            when(productImageRepository.save(existingProductImage)).thenReturn(existingProductImage);

            boolean result = productImageService.updateProductImage(productImageDto);

            assertTrue(result);
            verify(productImageRepository, times(1)).findById(productImageDto.getId());
            verify(productImageRepository, times(1)).save(existingProductImage);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productImageDto, existingProductImage), times(1));
        }
    }


    @Test
    void testUpdateProductImageNotFound() {
        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setId(1);

        when(productImageRepository.findById(productImageDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productImageService.updateProductImage(productImageDto));
        verify(productImageRepository, times(1)).findById(productImageDto.getId());
    }

    @Test
    void testDeleteProductImage() {
        int id = 1;
        ProductImage productImage = new ProductImage();
        productImage.setId(id);

        when(productImageRepository.findById(id)).thenReturn(Optional.of(productImage));
        doNothing().when(productImageRepository).deleteById(id);

        boolean result = productImageService.deleteProductImage(id);

        assertTrue(result);
        verify(productImageRepository, times(1)).findById(id);
        verify(productImageRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProductImageNotFound() {
        int id = 1;

        when(productImageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productImageService.deleteProductImage(id));
        verify(productImageRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllProductImages() {
        List<ProductImage> productImageList = new ArrayList<>();
        productImageList.add(new ProductImage());
        productImageList.add(new ProductImage());

        when(productImageRepository.findAll()).thenReturn(productImageList);

        List<ProductImage> result = productImageService.fetchAllProductImages();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productImageRepository, times(1)).findAll();
    }



}
