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

import com.catalog.dto.CategoryTagDto;
import com.catalog.entity.CategoryTag;
import com.catalog.repository.CategoryTagRepository;
import com.catalog.service.impl.CategoryTagServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class CategoryTagServiceImplTest {
	
	@Mock
    private CategoryTagRepository categoryTagRepository;

    @InjectMocks
    private CategoryTagServiceImpl categoryTagService;
    

    @Test
    void testCreateCategoryTag() {
    	
    	 CategoryTagDto categoryTagDto = new CategoryTagDto();
    	 categoryTagDto.setCategoryId(1);
    	 categoryTagDto.setTagId(1);
        
        
        CategoryTag categoryTag = new CategoryTag();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryTagDto, categoryTag))
                        .thenAnswer(invocation -> null);

            when(categoryTagRepository.save(any(CategoryTag.class))).thenReturn(categoryTag);

            categoryTagService.createCategoryTag(categoryTagDto);

            verify(categoryTagRepository, times(1)).save(any(CategoryTag.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryTagDto, categoryTag), times(1));
        }
    }
    
    @Test
    void testCreateCategoryTag_NullDto() {
       
        CategoryTagDto categoryTagDto = null;

        assertThrows(IllegalArgumentException.class, () -> categoryTagService.createCategoryTag(categoryTagDto));
        verify(categoryTagRepository, never()).save(any(CategoryTag.class));
    }
    
    @Test
    void testFetchCategoryTag() {
     
        int id = 1; 

        CategoryTag categoryTag = new CategoryTag();
        categoryTag.setId(id);
        categoryTag.setCategoryId(1);
        categoryTag.setTagId(1);


       when(categoryTagRepository.findById(id)).thenReturn(Optional.of(categoryTag));

        CategoryTag result = categoryTagService.fetchCategoryTag(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(1, result.getCategoryId());
        assertEquals(1, result.getTagId());
        verify(categoryTagRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchCategoryTagNotFound() {
        int id = 1;

        when(categoryTagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryTagService.fetchCategoryTag(id));
        verify(categoryTagRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateCategoryTag() {
        
    	CategoryTagDto categoryTagDto = new CategoryTagDto();
    	categoryTagDto.setId(1);
    	categoryTagDto.setCategoryId(2);
    	categoryTagDto.setTagId(2);

        CategoryTag existingCategoryTag = new CategoryTag();
        existingCategoryTag.setId(1); 
        existingCategoryTag.setCategoryId(2);
        existingCategoryTag.setTagId(2);

        when(categoryTagRepository.findById(categoryTagDto.getId())).thenReturn(Optional.of(existingCategoryTag));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryTagDto, existingCategoryTag))
                        .thenAnswer(invocation -> null);

            when(categoryTagRepository.save(existingCategoryTag)).thenReturn(existingCategoryTag);

            boolean result = categoryTagService.updateCategoryTag(categoryTagDto);

            assertTrue(result);
            verify(categoryTagRepository, times(1)).findById(categoryTagDto.getId());
            verify(categoryTagRepository, times(1)).save(existingCategoryTag);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryTagDto, existingCategoryTag), times(1));
        }
    }


    @Test
    void testUpdateCategoryTagNotFound() {
        CategoryTagDto categoryTagDto = new CategoryTagDto();
        categoryTagDto.setId(1);

        when(categoryTagRepository.findById(categoryTagDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryTagService.updateCategoryTag(categoryTagDto));
        verify(categoryTagRepository, times(1)).findById(categoryTagDto.getId());
    }

    @Test
    void testDeleteCategoryTag() {
        int id = 1;
        CategoryTag categoryTag = new CategoryTag();
        categoryTag.setId(id);

        when(categoryTagRepository.findById(id)).thenReturn(Optional.of(categoryTag));
        doNothing().when(categoryTagRepository).deleteById(id);

        boolean result = categoryTagService.deleteCategoryTag(id);

        assertTrue(result);
        verify(categoryTagRepository, times(1)).findById(id);
        verify(categoryTagRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteCategoryTagNotFound() {
        int id = 1;

        when(categoryTagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryTagService.deleteCategoryTag(id));
        verify(categoryTagRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllCategoryTags() {
        List<CategoryTag> categoryTagList = new ArrayList<>();
        categoryTagList.add(new CategoryTag());
        categoryTagList.add(new CategoryTag());

        when(categoryTagRepository.findAll()).thenReturn(categoryTagList);

        List<CategoryTag> result = categoryTagService.fetchAllCategoryTags();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryTagRepository, times(1)).findAll();
    }


}
