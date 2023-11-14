package com.noproject.bookstore.configuration;

import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.repository.AuthorRepository;
import com.noproject.bookstore.repository.BookRepository;
import com.noproject.bookstore.repository.CategoryBookRepository;
import com.noproject.bookstore.service.PaginationService;
import com.noproject.bookstore.service.extend.AuthorService;
import com.noproject.bookstore.service.extend.BookService;
import com.noproject.bookstore.service.extend.impl.AuthorServiceImpl;
import com.noproject.bookstore.service.extend.impl.BookServiceImpl;
import com.noproject.bookstore.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
//
//    @Bean
//    public PaginationService<Book> bookPaginationService(
//            BookRepository bookRepository,
//            CategoryBookRepository categoryBookRepository
//    ) {
//        return new BookServiceImpl(bookRepository, categoryBookRepository);
//    }

    @Bean
    public BookService bookService(
            BookRepository bookRepository,
            CategoryBookRepository categoryBookRepository,
            Validator<Book> bookValidator
    ) {
        return new BookServiceImpl(bookRepository, categoryBookRepository, bookValidator);
    }

    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorServiceImpl(authorRepository);
    }

}
