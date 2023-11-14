package com.noproject.bookstore.service.extend;

import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.service.CrudService;
import com.noproject.bookstore.service.PaginationService;

public interface BookService
        extends PaginationService<Book>, CrudService<Book, Long> {
}
