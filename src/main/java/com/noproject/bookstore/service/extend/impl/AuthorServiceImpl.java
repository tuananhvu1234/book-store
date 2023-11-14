package com.noproject.bookstore.service.extend.impl;

import com.noproject.bookstore.api.exception.NotFoundException;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.repository.AuthorRepository;
import com.noproject.bookstore.service.CrudService;
import com.noproject.bookstore.service.extend.AuthorService;
import com.noproject.bookstore.service.impl.CrudServiceImpl;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final CrudService<Author, Long> crudService;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.crudService = new CrudServiceImpl<>(authorRepository);
    }

    @Override
    public List<Author> findAll() {
        return crudService.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return crudService.findById(id);
    }

    @Override
    public Optional<Author> save(Author author) {
        return crudService.save(author);
    }

    @Override
    public Boolean delete(Author author) {

        final Example<Author> example = Example.of(author,
                ExampleMatcher.matching().withIgnorePaths(
                        "user.author"
                )
        );

        if (!authorRepository.exists(example)) {
            throw new NotFoundException();
        }

        return crudService.delete(author);
    }

    @Override
    public Boolean deleteById(Long id) {

        if (!authorRepository.existsById(id)) {
            throw new NotFoundException();
        }

        return crudService.deleteById(id);
    }

}
