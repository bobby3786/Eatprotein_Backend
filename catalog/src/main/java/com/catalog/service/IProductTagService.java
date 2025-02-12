package com.catalog.service;

import java.util.List;

import com.catalog.dto.ProductTagDto;
import com.catalog.entity.ProductTag;

public interface IProductTagService {
	
	void createProductTag(ProductTagDto productTagDto);
	ProductTag fetchProductTag(int id);
	boolean updateProductTag(ProductTagDto productTagDto);
	boolean deleteProductTag(int id);
	List<ProductTag> fetchAllProductTags();

}
