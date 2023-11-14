package com.noproject.bookstore.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<CategoryBook> books = new HashSet<>();

    @Override
    public String toString() {
        return String.format(
                "Category{id=%d, name=%s, description=%s}",
                id, name, description
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

        if (o instanceof Category category) {
            return compareTo(category) == 0 && books.equals(category.books);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, books);
    }

    @Override
    public int compareTo(@Nonnull Category o) {
        int result = id.compareTo(o.id);
        if (result != 0) {
            return result;
        }

        result = name.compareTo(o.name);
        if (result != 0) {
            return result;
        }

        result = description.compareTo(o.description);
        return result;
    }

}
