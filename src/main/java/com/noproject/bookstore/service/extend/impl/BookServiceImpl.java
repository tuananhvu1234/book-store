package com.noproject.bookstore.service.extend.impl;

import com.noproject.bookstore.api.exception.BadRequestException;
import com.noproject.bookstore.api.exception.InternalServerErrorException;
import com.noproject.bookstore.api.exception.NotFoundException;
import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.repository.BookRepository;
import com.noproject.bookstore.repository.CategoryBookRepository;
import com.noproject.bookstore.service.CrudService;
import com.noproject.bookstore.service.PaginationService;
import com.noproject.bookstore.service.extend.BookService;
import com.noproject.bookstore.service.impl.CrudServiceImpl;
import com.noproject.bookstore.service.impl.PaginationServiceImpl;
import com.noproject.bookstore.validator.Validator;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryBookRepository categoryBookRepository;

    private final CrudService<Book, Long> crudService;
    private final PaginationService<Book> paginationService;

    private final Validator<Book> bookValidator;

    public BookServiceImpl(
            BookRepository bookRepository,
            CategoryBookRepository categoryBookRepository,
            Validator<Book> bookValidator) {
        this.bookRepository = bookRepository;
        this.crudService = new CrudServiceImpl<>(bookRepository);
        this.paginationService = new PaginationServiceImpl<>(bookRepository);
        this.categoryBookRepository = categoryBookRepository;
        this.bookValidator = bookValidator;
    }

    @Override
    public Page<Book> findAll(int pageNumber, int pageSize) {
        return paginationService.findAll(pageNumber, pageSize);
    }

    @Override
    public List<Book> findAll() {
        return crudService.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return crudService.findById(id);
    }

    @Override
    public Optional<Book> save(Book book) {

        Validator.Result result = bookValidator.validate(book);
        if (result.isFail()) {
            throw new BadRequestException(result.getReason());
        }

        String shortDescription = book.getShortDescription();
        autoShortDescription:
        {
            if (Objects.isNull(shortDescription) || shortDescription.isBlank()) {
                String fullDescription = book.getFullDescription();
                if (Objects.isNull(fullDescription)) {
                    break autoShortDescription;
                }
                if (fullDescription.length() >= 100) {
                    book.setShortDescription(fullDescription.substring(0, 200).concat("..."));
                } else {
                    book.setShortDescription(fullDescription);
                }
            }
        }

        try {
            Book output = bookRepository.save(book);

            categoryBookRepository.saveAll(book.getCategories());

            return Optional.of(output);
        } catch (OptimisticLockingFailureException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new InternalServerErrorException();
        }
    }

    @Override
    public Boolean delete(Book book) {

        final Example<Book> example = Example.of(book, ExampleMatcher.matching()
                .withIgnorePaths("author.user")
        );

        if (!bookRepository.exists(example)) {
            throw new NotFoundException();
        }

        return crudService.delete(book);
    }

    @Override
    public Boolean deleteById(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new NotFoundException();
        }

        return crudService.deleteById(id);
    }

}
