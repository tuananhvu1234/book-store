package com.noproject.bookstore.configuration;

import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.validator.Validator;
import com.noproject.bookstore.validator.enitty.BookValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public Validator<Book> bookValidator() {
        return new BookValidator();
    }

}
