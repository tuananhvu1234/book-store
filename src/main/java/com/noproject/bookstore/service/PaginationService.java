package com.noproject.bookstore.service;

import org.springframework.data.domain.Page;

public interface PaginationService<E> {

    Page<E> findAll(int pageNumber, int pageSize);

}
