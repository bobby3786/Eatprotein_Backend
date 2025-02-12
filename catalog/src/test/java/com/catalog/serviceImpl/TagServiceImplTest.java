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

import com.catalog.dto.TagDto;
import com.catalog.entity.Tag;
import com.catalog.repository.TagRepository;
import com.catalog.service.impl.TagServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
	
	@Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;
    

    @Test
    void testCreateTag() {
    	
    	 TagDto tagDto = new TagDto();
    	 tagDto.setImagePath("tag/image.jpg");
        
        
        Tag tag = new Tag();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(tagDto, tag))
                        .thenAnswer(invocation -> null);

            when(tagRepository.save(any(Tag.class))).thenReturn(tag);

            tagService.createTag(tagDto);

            verify(tagRepository, times(1)).save(any(Tag.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(tagDto, tag), times(1));
        }
    }
    
    @Test
    void testCreateTag_NullDto() {
       
        TagDto tagDto = null;

        assertThrows(IllegalArgumentException.class, () -> tagService.createTag(tagDto));
        verify(tagRepository, never()).save(any(Tag.class));
    }
    
    @Test
    void testFetchTag() {
     
        int id = 1; 

        Tag tag = new Tag();
        tag.setId(id);
        tag.setImagePath("tag/image.jpg");


       when(tagRepository.findById(id)).thenReturn(Optional.of(tag));

        Tag result = tagService.fetchTag(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("tag/image.jpg", result.getImagePath()); 
        verify(tagRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchTagNotFound() {
        int id = 1;

        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.fetchTag(id));
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateTag() {
        
    	TagDto tagDto = new TagDto();
    	tagDto.setId(1);
    	tagDto.setImagePath("tag/image.jpg");

        Tag existingTag = new Tag();
        existingTag.setId(1); 
        existingTag.setImagePath("tag/image.jpg");

        when(tagRepository.findById(tagDto.getId())).thenReturn(Optional.of(existingTag));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(tagDto, existingTag))
                        .thenAnswer(invocation -> null);

            when(tagRepository.save(existingTag)).thenReturn(existingTag);

            boolean result = tagService.updateTag(tagDto);

            assertTrue(result);
            verify(tagRepository, times(1)).findById(tagDto.getId());
            verify(tagRepository, times(1)).save(existingTag);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(tagDto, existingTag), times(1));
        }
    }


    @Test
    void testUpdateTagNotFound() {
        TagDto tagDto = new TagDto();
        tagDto.setId(1);

        when(tagRepository.findById(tagDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.updateTag(tagDto));
        verify(tagRepository, times(1)).findById(tagDto.getId());
    }

    @Test
    void testDeleteTag() {
        int id = 1;
        Tag tag = new Tag();
        tag.setId(id);

        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
        doNothing().when(tagRepository).deleteById(id);

        boolean result = tagService.deleteTag(id);

        assertTrue(result);
        verify(tagRepository, times(1)).findById(id);
        verify(tagRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTagNotFound() {
        int id = 1;

        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.deleteTag(id));
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllTags() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag());
        tagList.add(new Tag());

        when(tagRepository.findAll()).thenReturn(tagList);

        List<Tag> result = tagService.fetchAllTags();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tagRepository, times(1)).findAll();
    }



}
