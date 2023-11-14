package com.noproject.bookstore.repository;

import com.noproject.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookRepository
        extends JpaRepository<Book, Long> {

    Set<Book> findAllByAuthorId(Long authorId);

}
