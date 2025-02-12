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

import com.catalog.dto.SubCategoryDto;
import com.catalog.entity.SubCategory;
import com.catalog.repository.SubCategoryRepository;
import com.catalog.service.impl.SubCategoryServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class SubCategoryServiceImplTest {
	
	@Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private SubCategoryServiceImpl subCategoryService;
    

    @Test
    void testCreateSubCategory() {
    	
    	 SubCategoryDto subCategoryDto = new SubCategoryDto();
    	 subCategoryDto.setName("SEEDS");
        
        
        SubCategory subCategory = new SubCategory();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(subCategoryDto, subCategory))
                        .thenAnswer(invocation -> null);

            when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(subCategory);

            subCategoryService.createSubCategory(subCategoryDto);

            verify(subCategoryRepository, times(1)).save(any(SubCategory.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(subCategoryDto, subCategory), times(1));
        }
    }
    
    @Test
    void testCreateSubCategory_NullDto() {
       
        SubCategoryDto subCategoryDto = null;

        assertThrows(IllegalArgumentException.class, () -> subCategoryService.createSubCategory(subCategoryDto));
        verify(subCategoryRepository, never()).save(any(SubCategory.class));
    }
    
    @Test
    void testFetchSubCategory() {
     
        int id = 1; 

        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);
        subCategory.setName("SEEDS");


       when(subCategoryRepository.findById(id)).thenReturn(Optional.of(subCategory));

        SubCategory result = subCategoryService.fetchSubCategory(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("SEEDS", result.getName()); 
        verify(subCategoryRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchSubCategoryNotFound() {
        int id = 1;

        when(subCategoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subCategoryService.fetchSubCategory(id));
        verify(subCategoryRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateSubCategory() {
        
    	SubCategoryDto subCategoryDto = new SubCategoryDto();
    	subCategoryDto.setId(1);
    	subCategoryDto.setName("SEEDS");

        SubCategory existingSubCategory = new SubCategory();
        existingSubCategory.setId(1); 
        existingSubCategory.setName("SEEDS");

        when(subCategoryRepository.findById(subCategoryDto.getId())).thenReturn(Optional.of(existingSubCategory));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(subCategoryDto, existingSubCategory))
                        .thenAnswer(invocation -> null);

            when(subCategoryRepository.save(existingSubCategory)).thenReturn(existingSubCategory);

            boolean result = subCategoryService.updateSubCategory(subCategoryDto);

            assertTrue(result);
            verify(subCategoryRepository, times(1)).findById(subCategoryDto.getId());
            verify(subCategoryRepository, times(1)).save(existingSubCategory);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(subCategoryDto, existingSubCategory), times(1));
        }
    }


    @Test
    void testUpdateSubCategoryNotFound() {
        SubCategoryDto subCategoryDto = new SubCategoryDto();
        subCategoryDto.setId(1);

        when(subCategoryRepository.findById(subCategoryDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subCategoryService.updateSubCategory(subCategoryDto));
        verify(subCategoryRepository, times(1)).findById(subCategoryDto.getId());
    }

    @Test
    void testDeleteSubCategory() {
        int id = 1;
        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);

        when(subCategoryRepository.findById(id)).thenReturn(Optional.of(subCategory));
        doNothing().when(subCategoryRepository).deleteById(id);

        boolean result = subCategoryService.deleteSubCategory(id);

        assertTrue(result);
        verify(subCategoryRepository, times(1)).findById(id);
        verify(subCategoryRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteSubCategoryNotFound() {
        int id = 1;

        when(subCategoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subCategoryService.deleteSubCategory(id));
        verify(subCategoryRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllSubCategorys() {
        List<SubCategory> subCategoryList = new ArrayList<>();
        subCategoryList.add(new SubCategory());
        subCategoryList.add(new SubCategory());

        when(subCategoryRepository.findAll()).thenReturn(subCategoryList);

        List<SubCategory> result = subCategoryService.fetchAllSubCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(subCategoryRepository, times(1)).findAll();
    }


}
