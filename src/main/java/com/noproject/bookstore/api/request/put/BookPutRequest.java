package com.noproject.bookstore.api.request.put;

import com.noproject.bookstore.dto.BookDTO;
import com.noproject.bookstore.dto.CategoryDTO;
import lombok.Data;

import java.util.List;

@Data
public class BookPutRequest {

    private BookDTO book;

    private List<Long> categories;

}
