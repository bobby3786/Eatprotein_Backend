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

import com.catalog.dto.ProductTagDto;
import com.catalog.entity.ProductTag;
import com.catalog.repository.ProductTagRepository;
import com.catalog.service.impl.ProductTagServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class ProductTagServiceImplTest {
	
	@Mock
    private ProductTagRepository productTagRepository;

    @InjectMocks
    private ProductTagServiceImpl productTagService;
    

    @Test
    void testCreateProductTag() {
    	
    	 ProductTagDto productTagDto = new ProductTagDto();
    	 productTagDto.setTagId(1);
        
        
        ProductTag productTag = new ProductTag();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productTagDto, productTag))
                        .thenAnswer(invocation -> null);

            when(productTagRepository.save(any(ProductTag.class))).thenReturn(productTag);

            productTagService.createProductTag(productTagDto);

            verify(productTagRepository, times(1)).save(any(ProductTag.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productTagDto, productTag), times(1));
        }
    }
    
    @Test
    void testCreateProductTag_NullDto() {
       
        ProductTagDto productTagDto = null;

        assertThrows(IllegalArgumentException.class, () -> productTagService.createProductTag(productTagDto));
        verify(productTagRepository, never()).save(any(ProductTag.class));
    }
    
    @Test
    void testFetchProductTag() {
     
        int id = 1; 

        ProductTag productTag = new ProductTag();
        productTag.setId(id);
        productTag.setTagId(1);


       when(productTagRepository.findById(id)).thenReturn(Optional.of(productTag));

        ProductTag result = productTagService.fetchProductTag(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(1, result.getTagId()); 
        verify(productTagRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchProductTagNotFound() {
        int id = 1;

        when(productTagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productTagService.fetchProductTag(id));
        verify(productTagRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateProductTag() {
        
    	ProductTagDto productTagDto = new ProductTagDto();
    	productTagDto.setId(1);
    	 productTagDto.setTagId(1);

        ProductTag existingProductTag = new ProductTag();
        existingProductTag.setId(1); 
        existingProductTag.setTagId(1);

        when(productTagRepository.findById(productTagDto.getId())).thenReturn(Optional.of(existingProductTag));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productTagDto, existingProductTag))
                        .thenAnswer(invocation -> null);

            when(productTagRepository.save(existingProductTag)).thenReturn(existingProductTag);

            boolean result = productTagService.updateProductTag(productTagDto);

            assertTrue(result);
            verify(productTagRepository, times(1)).findById(productTagDto.getId());
            verify(productTagRepository, times(1)).save(existingProductTag);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productTagDto, existingProductTag), times(1));
        }
    }


    @Test
    void testUpdateProductTagNotFound() {
        ProductTagDto productTagDto = new ProductTagDto();
        productTagDto.setId(1);

        when(productTagRepository.findById(productTagDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productTagService.updateProductTag(productTagDto));
        verify(productTagRepository, times(1)).findById(productTagDto.getId());
    }

    @Test
    void testDeleteProductTag() {
        int id = 1;
        ProductTag productTag = new ProductTag();
        productTag.setId(id);

        when(productTagRepository.findById(id)).thenReturn(Optional.of(productTag));
        doNothing().when(productTagRepository).deleteById(id);

        boolean result = productTagService.deleteProductTag(id);

        assertTrue(result);
        verify(productTagRepository, times(1)).findById(id);
        verify(productTagRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProductTagNotFound() {
        int id = 1;

        when(productTagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productTagService.deleteProductTag(id));
        verify(productTagRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllProductTags() {
        List<ProductTag> productTagList = new ArrayList<>();
        productTagList.add(new ProductTag());
        productTagList.add(new ProductTag());

        when(productTagRepository.findAll()).thenReturn(productTagList);

        List<ProductTag> result = productTagService.fetchAllProductTags();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productTagRepository, times(1)).findAll();
    }


}
