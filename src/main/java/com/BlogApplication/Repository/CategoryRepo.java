package com.BlogApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApplication.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
