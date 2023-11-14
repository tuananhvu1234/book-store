package com.noproject.bookstore.repository;

import com.noproject.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select cb.category from CategoryBook cb where cb.id.bookId = :bookId")
    List<Category> findAllByBookId(@Param("bookId") Long bookId);

}
