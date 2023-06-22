package com.BlogApplication.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApplication.Entity.Category;
import com.BlogApplication.Payload.CategoryDto;
import com.BlogApplication.Payload.UserDto;
import com.BlogApplication.Service.CategoryService;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
	

	
		@PutMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
			CategoryDto updatedcategory = this.categoryService.updateCategory(categoryDto, categoryId);
			return ResponseEntity.ok(updatedcategory);
		}
		
		//DELETE-delete user
		
		@DeleteMapping("/{catId}")
		public ResponseEntity<String> deleteUser(@PathVariable Integer catId) {
			
			this.categoryService.deleteCategory(catId);
			return new ResponseEntity<String>("Category deleted", HttpStatus.ACCEPTED);
		}
		
		
		//GET-user get
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
			return ResponseEntity.ok(this.categoryService.getAllCategories());
		}
		
		//GET-user get
		@GetMapping("/{catId}")
		public ResponseEntity<CategoryDto> getAllCategory(@PathVariable Integer catId){		
				return ResponseEntity.ok(this.categoryService.getCategoryById(catId));
		}
		
	
	
}
