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

import com.catalog.dto.CategoryDto;
import com.catalog.entity.Category;
import com.catalog.repository.CategoryRepository;
import com.catalog.service.impl.CategoryServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
	
	@Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;
    

    @Test
    void testCreateCategory() {
    	
    	 CategoryDto categoryDto = new CategoryDto();
    	 categoryDto.setStatus("Active");
        
        
        Category category = new Category();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryDto, category))
                        .thenAnswer(invocation -> null);

            when(categoryRepository.save(any(Category.class))).thenReturn(category);

            categoryService.createCategory(categoryDto);

            verify(categoryRepository, times(1)).save(any(Category.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryDto, category), times(1));
        }
    }
    
    @Test
    void testCreateCategory_NullDto() {
       
        CategoryDto categoryDto = null;

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(categoryDto));
        verify(categoryRepository, never()).save(any(Category.class));
    }
    
    @Test
    void testFetchCategory() {
     
        int id = 1; 

        Category category = new Category();
        category.setId(id);
        category.setStatus("Active"); 


       when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Category result = categoryService.fetchCategory(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("Active", result.getStatus()); 
        verify(categoryRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchCategoryNotFound() {
        int id = 1;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.fetchCategory(id));
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateCategory() {
        
    	CategoryDto categoryDto = new CategoryDto();
    	categoryDto.setId(1);
    	categoryDto.setStatus("Active");

        Category existingCategory = new Category();
        existingCategory.setId(1); 
        existingCategory.setStatus("InActive"); 

        when(categoryRepository.findById(categoryDto.getId())).thenReturn(Optional.of(existingCategory));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryDto, existingCategory))
                        .thenAnswer(invocation -> null);

            when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

            boolean result = categoryService.updateCategory(categoryDto);

            assertTrue(result);
            verify(categoryRepository, times(1)).findById(categoryDto.getId());
            verify(categoryRepository, times(1)).save(existingCategory);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(categoryDto, existingCategory), times(1));
        }
    }


    @Test
    void testUpdateCategoryNotFound() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1);

        when(categoryRepository.findById(categoryDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(categoryDto));
        verify(categoryRepository, times(1)).findById(categoryDto.getId());
    }

    @Test
    void testDeleteCategory() {
        int id = 1;
        Category category = new Category();
        category.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        boolean result = categoryService.deleteCategory(id);

        assertTrue(result);
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategoryNotFound() {
        int id = 1;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(id));
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllCategorys() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        categoryList.add(new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.fetchAllCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

}
