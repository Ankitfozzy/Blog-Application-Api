package com.BlogApplication.Service;

import java.util.List;

import com.BlogApplication.Payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto CategoryDto);
	
	CategoryDto updateCategory(CategoryDto CategoryDto, Integer CategoryId);
	
	String deleteCategory(Integer CategoryId);
	
	CategoryDto getCategoryById(Integer CategoryId);
	
	List<CategoryDto> getAllCategories();
}
