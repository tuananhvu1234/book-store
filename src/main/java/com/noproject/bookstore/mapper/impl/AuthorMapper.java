package com.noproject.bookstore.mapper.impl;

import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.entity.User;
import com.noproject.bookstore.mapper.Mapper;
import com.noproject.bookstore.repository.BookRepository;
import com.noproject.bookstore.repository.UserRepository;

import java.util.Optional;

public class AuthorMapper extends Mapper<AuthorDTO, Author> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public AuthorMapper(BookRepository bookRepository, UserRepository userRepository) {
        super(Author.class);
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void handleMapping(AuthorDTO authorDTO, Author author) {

        final Long authorId = authorDTO.getId();

        author.setId(authorId);

        author.setPenName(authorDTO.getPenName());

        author.setBooks(bookRepository.findAllByAuthorId(authorId));

        Optional<User> optional = userRepository.findByAuthorId(authorId);
        optional.ifPresent(author::setUser);
    }
}
