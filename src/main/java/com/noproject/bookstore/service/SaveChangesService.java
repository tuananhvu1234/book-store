package com.noproject.bookstore.service;

import java.util.Optional;

public interface SaveChangesService<T> {

    Optional<T> save(T obj);

}
