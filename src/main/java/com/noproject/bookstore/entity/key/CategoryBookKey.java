package com.noproject.bookstore.entity.key;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CategoryBookKey
        implements Serializable, Comparable<CategoryBookKey> {

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "category_id")
    private Long categoryId;

    @Override
    public String toString() {
        return String.format(
                "CategoryBookKey(bookId=%d, categoryId=%d)",
                bookId, categoryId
        );
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof CategoryBookKey key) {
            return Objects.equals(bookId, key.bookId) && Objects.equals(categoryId, key.categoryId);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, categoryId);
    }

    @Override
    public int compareTo(@Nonnull CategoryBookKey o) {

        int result = bookId.compareTo(o.bookId);
        if (result != 0) {
            return result;
        }

        result = categoryId.compareTo(o.categoryId);
        return result;
    }

}
