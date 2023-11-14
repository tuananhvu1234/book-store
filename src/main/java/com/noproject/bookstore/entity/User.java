package com.noproject.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean status;

    @OneToOne(mappedBy = "user", cascade = ALL)
    @PrimaryKeyJoinColumn
    private Author author;

    @Override
    public String toString() {
        return String.format(
                "User{id=%d, username=%s, password=%s, status=%b}",
                id, username, password, status
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

        if (o instanceof User otherUser) {
            return compareTo(otherUser) == 0 && author.compareTo(otherUser.author) == 0;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, status, author);
    }

    @Override
    public int compareTo(User o) {
        int result = id.compareTo(o.id);
        if (result != 0) {
            return result;
        }

        result = username.compareTo(o.username);
        if (result != 0) {
            return result;
        }

        result = password.compareTo(o.password);
        if (result != 0) {
            return result;
        }

        result = status.compareTo(o.status);
        return result;
    }

}
