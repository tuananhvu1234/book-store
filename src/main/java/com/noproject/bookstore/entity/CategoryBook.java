package com.noproject.bookstore.entity;

import com.noproject.bookstore.entity.key.CategoryBookKey;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_book")
public class CategoryBook implements Comparable<CategoryBook> {

    @EmbeddedId
    private CategoryBookKey id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public String toString() {
        return String.format("CategoryBook{id=%s}", id);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof CategoryBook categoryBook) {
//            return id.equals(categoryBook.id);
            return compareTo(categoryBook) == 0;
        }

        return false;
    }

    @Override
    public int hashCode() {
//        return id.hashCode();
        return Objects.hash(id,
                book.getId(), book.getTitle(), book.getShortDescription(),
                book.getFullDescription(), book.getImage(), book.getPrice(), book.getQuantity(),
                category.getId(), category.getName(), category.getDescription()
        );
    }

    @Override
    public int compareTo(@Nonnull CategoryBook o) {
//        return id.compareTo(o.id);
        int result = id.compareTo(o.id);
        if (result != 0) {
            return result;
        }

        result = book.compareTo(o.book);
        if (result != 0) {
            return result;
        }

        result = category.compareTo(o.category);
        return result;
    }

}
