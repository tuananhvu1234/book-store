package com.noproject.bookstore.configuration;

import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.dto.BookDTO;
import com.noproject.bookstore.dto.CategoryDTO;

import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.entity.Category;

import com.noproject.bookstore.mapper.Mapper;

import com.noproject.bookstore.mapper.impl.AuthorDTOMapper;
import com.noproject.bookstore.mapper.impl.AuthorMapper;
import com.noproject.bookstore.mapper.impl.BookDTOMapper;
import com.noproject.bookstore.mapper.impl.BookMapper;
import com.noproject.bookstore.mapper.impl.CategoryDTOMapper;
import com.noproject.bookstore.mapper.impl.CategoryMapper;

import com.noproject.bookstore.repository.BookRepository;
import com.noproject.bookstore.repository.CategoryBookRepository;
import com.noproject.bookstore.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public Mapper<Author, AuthorDTO> authorDTOMapper() {
        return new AuthorDTOMapper();
    }

    @Bean
    public Mapper<Category, CategoryDTO> categoryDTOMapper() {
        return new CategoryDTOMapper();
    }

    @Bean
    public Mapper<Book, BookDTO> bookDTOMapper(
            Mapper<Author, AuthorDTO> authorDTOMapper,
            Mapper<Category, CategoryDTO> categoryDTOMapper
    ) {
        return new BookDTOMapper(authorDTOMapper, categoryDTOMapper);
    }

    @Bean
    public Mapper<AuthorDTO, Author> authorMapper(
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        return new AuthorMapper(bookRepository, userRepository);
    }

    @Bean
    public Mapper<BookDTO, Book> bookMapper(
            CategoryBookRepository categoryBookRepository,
            Mapper<AuthorDTO, Author> authorMapper,
            Mapper<CategoryDTO, Category> categoryMapper
    ) {
        return new BookMapper(categoryBookRepository, authorMapper, categoryMapper);
    }

    @Bean
    public Mapper<CategoryDTO, Category> categoryMapper(
            CategoryBookRepository categoryBookRepository
    ) {
        return new CategoryMapper(categoryBookRepository);
    }

}
