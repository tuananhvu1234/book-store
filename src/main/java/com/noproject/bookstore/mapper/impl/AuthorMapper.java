package com.noproject.bookstore.mapper.impl;

import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.entity.User;
import com.noproject.bookstore.mapper.Mapper;
import com.noproject.bookstore.repository.BookRepository;
import com.noproject.bookstore.repository.UserRepository;

import java.util.Objects;
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

        String fullName = authorDTO.getFullName();
        if (!Objects.isNull(fullName)) {
            final int firstWhiteSpaceIndex = fullName.indexOf(" ");
            author.setFirstName(fullName.substring(0, firstWhiteSpaceIndex));
            author.setLastName(fullName.substring(firstWhiteSpaceIndex + 1));
        }

        Optional<User> optional = userRepository.findByAuthorId(authorId);
        optional.ifPresent(author::setUser);
    }
}
