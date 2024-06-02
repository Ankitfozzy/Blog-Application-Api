package com.BlogApplication.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApplication.Entity.Category;
import com.BlogApplication.Exceptions.ResourceNotFoundException;
import com.BlogApplication.Payload.CategoryDto;
import com.BlogApplication.Repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addcategory = this.categoryRepo.save(category);
		return this.modelMapper.map(addcategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category updatedcategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", categoryId));
		updatedcategory.setCategoryTitle(categoryDto.getCategoryTitle());
		updatedcategory.setCategoryDescription(categoryDto.getCategoryDescription());
		this.categoryRepo.save(updatedcategory);
		return this.modelMapper.map(updatedcategory, CategoryDto.class);
	}

	@Override
	public String deleteCategory(Integer categoryId) {

		Category deleteCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", categoryId));
		this.categoryRepo.delete(deleteCategory);

		return "Category deleted!";
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", categoryId));

		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {

		List<Category> cateroies = this.categoryRepo.findAll();
		List<CategoryDto> catDto = cateroies.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());

		return catDto;
	}

}
