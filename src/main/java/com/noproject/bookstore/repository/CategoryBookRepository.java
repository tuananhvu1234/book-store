package com.noproject.bookstore.repository;

import com.noproject.bookstore.entity.CategoryBook;
import com.noproject.bookstore.entity.key.CategoryBookKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryBookRepository extends JpaRepository<CategoryBook, CategoryBookKey> {

    @Query("select cb.id.categoryId from CategoryBook cb where cb.id.bookId = :bookId")
    List<Long> findAllCategoryIdByBookId(Long bookId);

    Optional<CategoryBook> findByCategoryId(Long categoryId);

    Set<CategoryBook> findAllByCategoryId(Long categoryId);

    Set<CategoryBook> findAllByBookId(Long bookId);

}
