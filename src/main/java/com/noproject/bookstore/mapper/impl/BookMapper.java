package com.noproject.bookstore.mapper.impl;

import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.dto.BookDTO;
import com.noproject.bookstore.dto.CategoryDTO;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.entity.Category;
import com.noproject.bookstore.entity.CategoryBook;
import com.noproject.bookstore.entity.key.CategoryBookKey;
import com.noproject.bookstore.mapper.Mapper;
import com.noproject.bookstore.repository.CategoryBookRepository;

import java.util.HashSet;
import java.util.Set;

public class BookMapper extends Mapper<BookDTO, Book> {

    private final CategoryBookRepository categoryBookRepository;

    private final Mapper<AuthorDTO, Author> authorMapper;
    private final Mapper<CategoryDTO, Category> categoryMapper;

    public BookMapper(
            CategoryBookRepository categoryBookRepository,
            Mapper<AuthorDTO, Author> authorMapper,
            Mapper<CategoryDTO, Category> categoryMapper
    ) {
        super(Book.class);
        this.categoryBookRepository = categoryBookRepository;
        this.authorMapper = authorMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    protected void handleMapping(BookDTO bookDto, Book book) {

        final Long bookId = bookDto.getId();

        book.setId(bookId);

        book.setTitle(bookDto.getTitle());

        book.setShortDescription(bookDto.getShortDescription());

        book.setFullDescription(bookDto.getFullDescription());

        book.setImage(bookDto.getImage());

        book.setPrice(bookDto.getPrice());

        book.setQuantity(bookDto.getQuantity());

        book.setAuthor(authorMapper.map(bookDto.getAuthor()));

        book.setCategories(new HashSet<>());

        Set<CategoryBook> categoryBooks = book.getCategories();

        Set<CategoryDTO> categoryDTOS = bookDto.getCategories();

        for (CategoryDTO categoryDTO : categoryDTOS) {
            CategoryBookKey key = new CategoryBookKey(bookId, categoryDTO.getId());

            CategoryBook categoryBook = new CategoryBook(key, book, categoryMapper.map(categoryDTO));

            categoryBooks.add(categoryBook);
        }

    }

}
