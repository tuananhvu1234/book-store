package com.noproject.bookstore.controller;

import com.noproject.bookstore.api.exception.BadRequestException;
import com.noproject.bookstore.api.exception.InternalServerErrorException;
import com.noproject.bookstore.api.query.ApiRequest;
import com.noproject.bookstore.api.query.ApiResponse;
import com.noproject.bookstore.api.query.ResponseInformation;
import com.noproject.bookstore.api.request.PaginationRequest;
import com.noproject.bookstore.dto.BookDTO;
import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.mapper.Mapper;
import com.noproject.bookstore.service.extend.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final Mapper<Book, BookDTO> bookDTOMapper;
    private final Mapper<BookDTO, Book> bookMapper;

    private final BookService bookService;

    public BookController(
            BookService bookService,
            Mapper<Book, BookDTO> bookDTOMapper,
            Mapper<BookDTO, Book> bookMapper
    ) {
        this.bookService = bookService;

        this.bookDTOMapper = bookDTOMapper;
        this.bookMapper = bookMapper;
    }

    @GetMapping({"/pagination"})
    public Page<Book> getAll(PaginationRequest request) {
        return bookService.findAll(request.getPage(), request.getSize());
    }

    @GetMapping({"", "/"})
    public ApiResponse<List<?>> getAll(ApiRequest<BookDTO> request) {
        return request.getResponseArray(
                new ResponseInformation(HttpStatus.OK),
                bookDTOMapper.map(bookService.findAll(), new ArrayList<>() {
                })
        );
    }

    @GetMapping({"/{id}"})
    public ApiResponse<?> getOne(ApiRequest<BookDTO> request, @PathVariable("id") Long id) {

        return request.getResponseObject(
                new ResponseInformation(HttpStatus.OK),
                bookDTOMapper.map(bookService.findById(id).orElse(null))
        );
    }

    @PostMapping({"", "/"})
    public ApiResponse<?> create(@RequestBody BookDTO bookDTO) {
        if (Objects.isNull(bookDTO.getTitle())) {
            throw new BadRequestException("The title of book is required.");
        }

        if (Objects.isNull(bookDTO.getCategories()) || bookDTO.getCategories().isEmpty()) {
            throw new BadRequestException("The book need at least 1 genre.");
        }

        if (Objects.isNull(bookDTO.getAuthor())) {
            throw new BadRequestException();
        }

        if (Objects.isNull(bookDTO.getPrice()) || bookDTO.getPrice() <= 0) {
            bookDTO.setPrice(0L);
        }

        if (Objects.isNull(bookDTO.getQuantity()) || bookDTO.getQuantity() <= 0) {
            bookDTO.setQuantity(0);
        }

        Optional<Book> optional = bookService.save(bookMapper.map(bookDTO));
        if (optional.isPresent()) {
            return new ApiResponse<>(HttpStatus.CREATED);
        } else {
            throw new InternalServerErrorException();
        }
    }

    @PutMapping({"/{id}"})
    public ApiResponse<Void> update(
            @PathVariable("id") Long id,
            @RequestBody BookDTO bookDTO
    ) {
        Long bookId = bookDTO.getId();
        if (bookId == null || !bookId.equals(id)) {
            throw new BadRequestException();
        }

        Book book = bookMapper.map(bookDTO);
        bookService.save(book);

        return new ApiResponse<>(HttpStatus.NO_CONTENT);
//
//        return new BookPutRequestHandler(
//                bookRepository,
//                categoryRepository,
//                categoryBookRepository
//        ).handle(book);
    }

}
