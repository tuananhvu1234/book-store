package com.noproject.bookstore.mapper.impl;

import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.mapper.Mapper;

public class AuthorDTOMapper extends Mapper<Author, AuthorDTO> {

    public AuthorDTOMapper() {
        super(AuthorDTO.class);
    }

    @Override
    protected void handleMapping(Author author, AuthorDTO authorDto) {

        authorDto.setId(author.getId());

        authorDto.setPenName(author.getPenName());

    }

}
