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

import com.catalog.dto.ProductDto;
import com.catalog.entity.Product;
import com.catalog.repository.ProductRepository;
import com.catalog.service.impl.ProductServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	
	@Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    

    @Test
    void testCreateProduct() {
    	
    	 ProductDto productDto = new ProductDto();
//    	 productDto.setCategoryId(1);
		  productDto.setProductName("MOBILE");
        
        
        Product product = new Product();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productDto, product))
                        .thenAnswer(invocation -> null);

            when(productRepository.save(any(Product.class))).thenReturn(product);

            productService.createProduct(productDto);

            verify(productRepository, times(1)).save(any(Product.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productDto, product), times(1));
        }
    }
    
    @Test
    void testCreateProduct_NullDto() {
       
        ProductDto ProductDto = null;

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(ProductDto));
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void testFetchProduct() {
     
        int id = 1; 

        Product product = new Product();
        product.setId(id);
		  product.setProductName("MOBILE");


       when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.fetchProduct(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("MOBILE", result.getProductName());
        verify(productRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchProductNotFound() {
        int id = 1;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.fetchProduct(id));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateProduct() {
        
    	ProductDto productDto = new ProductDto();
    	productDto.setId(1);
    	productDto.setProductName("MOBILE");

        Product existingProduct = new Product();
        existingProduct.setId(1); 
        existingProduct.setProductName("LAPTOP");

        when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(existingProduct));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(productDto, existingProduct))
                        .thenAnswer(invocation -> null);

            when(productRepository.save(existingProduct)).thenReturn(existingProduct);

            boolean result = productService.updateProduct(productDto);

            assertTrue(result);
            verify(productRepository, times(1)).findById(productDto.getId());
            verify(productRepository, times(1)).save(existingProduct);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(productDto, existingProduct), times(1));
        }
    }


    @Test
    void testUpdateProductNotFound() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1);

        when(productRepository.findById(productDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productDto));
        verify(productRepository, times(1)).findById(productDto.getId());
    }

    @Test
    void testDeleteProduct() {
        int id = 1;
        Product product = new Product();
        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        boolean result = productService.deleteProduct(id);

        assertTrue(result);
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProductNotFound() {
        int id = 1;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(id));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.fetchAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }


}
