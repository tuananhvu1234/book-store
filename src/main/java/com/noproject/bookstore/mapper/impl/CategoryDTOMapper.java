package com.noproject.bookstore.mapper.impl;

import com.noproject.bookstore.dto.CategoryDTO;
import com.noproject.bookstore.entity.Category;
import com.noproject.bookstore.mapper.Mapper;

public class CategoryDTOMapper extends Mapper<Category, CategoryDTO> {

    public CategoryDTOMapper() {
        super(CategoryDTO.class);
    }

    @Override
    protected void handleMapping(Category provider, CategoryDTO consumer) {
        consumer.setId(provider.getId());
        consumer.setName(provider.getName());
        consumer.setDescription(provider.getDescription());
    }

}
