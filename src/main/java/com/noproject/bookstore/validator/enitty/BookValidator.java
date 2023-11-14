package com.noproject.bookstore.validator.enitty;

import com.noproject.bookstore.entity.Book;
import com.noproject.bookstore.entity.CategoryBook;
import com.noproject.bookstore.validator.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BookValidator extends Validator<Book> {

    private static final Map<String, Tester<Book>> TESTERS;

    static {
        TESTERS = new HashMap<>() {{
            put("title", book -> {
                String title = book.getTitle();
                return Objects.nonNull(title) && !title.isBlank();
            });
            put("author", book -> Objects.nonNull(book.getAuthor()));
            put("categories", book -> {
                Set<CategoryBook> categories = book.getCategories();
                return Objects.nonNull(categories) && !categories.isEmpty();
            });
        }};
    }

    public BookValidator() {
        super(TESTERS);
    }

}
