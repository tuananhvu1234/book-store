package com.noproject.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "books")
public class Book implements Comparable<Book> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(name = "short_description", length = 200)
    private String shortDescription;

    @Column(name = "full_description", columnDefinition = "text")
    private String fullDescription;

    @Column(length = 300)
    private String image;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "book")
    private Set<CategoryBook> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Override
    public String toString() {
        return String.format(
                "Book{id=%d, title=%s, shortDescription=%s, fullDescription=%s,"
                        + " image=%s, price=%d, quantity=%d, categories=%s, author=%s"
                        + "}",
                id, title, shortDescription, fullDescription,
                image, price, quantity, categories, author
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (o instanceof Book otherBook) {
            return compareTo(otherBook) == 0 && author.compareTo(otherBook.author) == 0;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, shortDescription, fullDescription, image, price, quantity, categories,
                author.getId(), author.getFirstName(), author.getLastName(), author.getPenName()
        );
    }

    @Override
    public int compareTo(Book o) {

        int result = id.compareTo(o.id);
        if (result != 0) {
            return result;
        }

        result = title.compareTo(o.title);
        if (result != 0) {
            return result;
        }

        result = shortDescription.compareTo(o.shortDescription);
        if (result != 0) {
            return result;
        }

        result = fullDescription.compareTo(o.fullDescription);
        if (result != 0) {
            return result;
        }

        result = image.compareTo(o.image);
        if (result != 0) {
            return result;
        }

        result = price.compareTo(o.price);
        if (result != 0) {
            return result;
        }

        result = quantity.compareTo(o.quantity);
        return result;
    }

}
