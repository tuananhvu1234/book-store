package com.noproject.bookstore.service.extend.impl;

import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.entity.CategoryBook;
import com.noproject.bookstore.exception.BadRequestException;
import com.noproject.bookstore.exception.InternalServerErrorException;
import com.noproject.bookstore.exception.NotFoundException;
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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

        String description = book.getShortDescription();
        if (Objects.isNull(description) || description.isBlank()) {

            description = book.getFullDescription();
            if (Objects.nonNull(description)) {
                if (description.length() >= 100) {
                    book.setShortDescription(description.substring(0, 100).concat("..."));
                } else {
                    book.setShortDescription(description);
                }
            }
        }

        try {
            Book output;
            if (book.getId() != null) {
                // update must already exist categories in database
                saveBookCategory(book);
                output = bookRepository.save(book);
            } else {
                // insert must already exist book in database
                output = bookRepository.save(book);
                saveBookCategory(output);
            }
            return Optional.of(output);
        } catch (OptimisticLockingFailureException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new InternalServerErrorException();
        }
    }

    private void saveBookCategory(Book book) {
        Set<CategoryBook> existCategoryBooks = categoryBookRepository.findAllByBookId(book.getId());
        if (existCategoryBooks.isEmpty()) {
            categoryBookRepository.saveAll(book.getCategories());
        } else {
            Set<CategoryBook> newCategoryBooks = book.getCategories();

            Set<CategoryBook> mergedCategoryBooks = new HashSet<>();

            mergedCategoryBooks.addAll(existCategoryBooks);
            mergedCategoryBooks.addAll(newCategoryBooks);

            for (CategoryBook categoryBook : mergedCategoryBooks) {
                if (existCategoryBooks.contains(categoryBook)
                        && !newCategoryBooks.contains(categoryBook)) {
                    categoryBookRepository.delete(categoryBook);
                } else if (!existCategoryBooks.contains(categoryBook)
                        && newCategoryBooks.contains(categoryBook)) {
                    categoryBookRepository.save(categoryBook);
                }
            }
        }
    }

    @Override
    public Boolean delete(Book book) {

        if (Objects.isNull(book)) {
            throw new BadRequestException();
        }

        final Example<Book> example = Example.of(book, ExampleMatcher.matching()
                .withIgnorePaths("author.user")
        );

        if (!bookRepository.exists(example)) {
            throw new NotFoundException();
        }

        categoryBookRepository.deleteAll(categoryBookRepository.findAllByBookId(book.getId()));

        return crudService.delete(book);
    }

    @Override
    public Boolean deleteById(Long id) {

        if (Objects.isNull(id)) {
            throw new BadRequestException();
        }

        if (!bookRepository.existsById(id)) {
            throw new NotFoundException();
        }

        categoryBookRepository.deleteAll(categoryBookRepository.findAllByBookId(id));

        return crudService.deleteById(id);
    }

}
