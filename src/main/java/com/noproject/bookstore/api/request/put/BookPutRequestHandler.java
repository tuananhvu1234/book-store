package com.noproject.bookstore.api.request.put;

import com.noproject.bookstore.api.exception.BadRequestException;
import com.noproject.bookstore.api.query.ApiResponse;
import com.noproject.bookstore.dto.BookDTO;
import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.entity.Category;
import com.noproject.bookstore.entity.CategoryBook;
import com.noproject.bookstore.entity.key.CategoryBookKey;
import com.noproject.bookstore.repository.BookRepository;
import com.noproject.bookstore.repository.CategoryBookRepository;
import com.noproject.bookstore.repository.CategoryRepository;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookPutRequestHandler {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryBookRepository categoryBookRepository;

    public BookPutRequestHandler(
            BookRepository bookRepository,
            CategoryRepository categoryRepository,
            CategoryBookRepository categoryBookRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.categoryBookRepository = categoryBookRepository;
    }

    public ApiResponse<BookDTO> handle(BookPutRequest request) {
        final BookDTO bookDTO = request.getBook();

        Optional<Book> existingBook = bookRepository.findById(bookDTO.getId());

        if (existingBook.isEmpty()) {
            throw new BadRequestException();
        }

        final Book book = existingBook.get();

        book.setTitle(bookDTO.getTitle());
        book.setShortDescription(bookDTO.getShortDescription());
        book.setFullDescription(bookDTO.getFullDescription());
        book.setImage(bookDTO.getImage());
        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());

        bookRepository.save(book);

        if (request.getCategories() != null) {
            List<Long> existingCateIds = categoryBookRepository.findAllCategoryIdByBookId(book.getId());
            List<Long> categoryDTOIdS = request.getCategories();
            List<Long> removeCateIds = new ArrayList<>();

            existingCateIds.forEach(cateId -> {
                if (!categoryDTOIdS.contains(cateId)) {
                    removeCateIds.add(cateId);
                }
            });

            removeCateIds.forEach(cateId -> {
                Optional<CategoryBook> optional = categoryBookRepository.findById(
                        new CategoryBookKey(book.getId(), cateId)
                );
                optional.ifPresent(categoryBookRepository::delete);
            });

            categoryDTOIdS.forEach(categoryId -> {

                CategoryBookKey key = new CategoryBookKey(book.getId(), categoryId);

                Optional<CategoryBook> optional = categoryBookRepository.findById(key);
                Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

                if (optional.isEmpty() && optionalCategory.isPresent()) {
                    CategoryBook categoryBook = new CategoryBook();

                    categoryBook.setId(key);
                    categoryBook.setBook(book);
                    categoryBook.setCategory(optionalCategory.get());

                    categoryBookRepository.save(categoryBook);
                }

            });

        }

        return new ApiResponse<>(HttpStatus.NO_CONTENT);
    }

}
