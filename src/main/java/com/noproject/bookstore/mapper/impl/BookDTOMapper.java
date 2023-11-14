package com.noproject.bookstore.mapper.impl;

import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.dto.BookDTO;
import com.noproject.bookstore.dto.CategoryDTO;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.entity.Category;
import com.noproject.bookstore.mapper.Mapper;

import java.util.stream.Collectors;

public class BookDTOMapper extends Mapper<Book, BookDTO> {

    private final Mapper<Author, AuthorDTO> authorDTOMapper;
    private final Mapper<Category, CategoryDTO> categoryDTOMapper;

    public BookDTOMapper(
            Mapper<Author, AuthorDTO> authorDTOMapper,
            Mapper<Category, CategoryDTO> categoryDTOMapper
    ) {
        super(BookDTO.class);
        this.authorDTOMapper = authorDTOMapper;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    @Override
    protected void handleMapping(Book book, BookDTO bookDto) {

        bookDto.setId(book.getId());

        bookDto.setTitle(book.getTitle());

        bookDto.setShortDescription(book.getShortDescription());

        bookDto.setFullDescription(book.getFullDescription());

        bookDto.setImage(book.getImage());

        bookDto.setPrice(book.getPrice());

        bookDto.setQuantity(book.getQuantity());

        bookDto.setAuthor(authorDTOMapper.map(book.getAuthor()));

        bookDto.setCategories(book.getCategories().stream().map(
                categoryBook -> categoryDTOMapper.map(categoryBook.getCategory())
        ).collect(Collectors.toSet()));

    }

}
