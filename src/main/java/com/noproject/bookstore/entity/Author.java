package com.noproject.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author implements Comparable<Author> {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "pen_name", length = 100)
    private String penName;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    @Override
    public String toString() {
        return String.format(
                "Author{id=%d, firstName=%s, lastName==%s, penName=%s, user=%s}",
                id, firstName, lastName, penName, user
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

        if (o instanceof Author author) {
            return compareTo(author) == 0
                    && user.compareTo(author.user) == 0
                    && books.equals(author.books);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, penName, books,
                user.getId(), user.getUsername(), user.getPassword(), user.getStatus()
        );
    }

    @Override
    public int compareTo(Author o) {
        int result = id.compareTo(o.id);
        if (result != 0) {
            return result;
        }

        result = firstName.compareTo(o.firstName);
        if (result != 0) {
            return result;
        }

        result = lastName.compareTo(o.lastName);
        if (result != 0) {
            return result;
        }

        result = penName.compareTo(o.penName);
        return result;
    }

}
