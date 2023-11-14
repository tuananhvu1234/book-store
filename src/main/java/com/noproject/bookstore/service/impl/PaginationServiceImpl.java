package com.noproject.bookstore.service.impl;

import com.noproject.bookstore.service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public class PaginationServiceImpl<T, ID> implements PaginationService<T> {

    private final JpaRepository<T, ID> repository;

    public PaginationServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public Page<T> findAll(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }

        if (pageSize < 1) {
            pageSize = 1;
        }

        return repository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber));
    }

}
