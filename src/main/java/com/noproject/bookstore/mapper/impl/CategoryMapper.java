package com.noproject.bookstore.mapper.impl;

import com.noproject.bookstore.dto.CategoryDTO;
import com.noproject.bookstore.entity.Category;
import com.noproject.bookstore.mapper.Mapper;
import com.noproject.bookstore.repository.CategoryBookRepository;

public class CategoryMapper extends Mapper<CategoryDTO, Category> {

    private final CategoryBookRepository categoryBookRepository;

    public CategoryMapper(CategoryBookRepository categoryBookRepository) {
        super(Category.class);
        this.categoryBookRepository = categoryBookRepository;
    }

    @Override
    protected void handleMapping(CategoryDTO categoryDTO, Category category) {

        final Long categoryId = categoryDTO.getId();
        category.setId(categoryId);

        category.setName(categoryDTO.getName());

        category.setDescription(categoryDTO.getDescription());

        category.setBooks(categoryBookRepository.findAllByCategoryId(categoryId));

    }

}
