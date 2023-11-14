package com.noproject.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private String shortDescription;

    private String fullDescription;

    private Set<CategoryDTO> categories;

    private AuthorDTO author;

    private String image;

    private Long price;

    private Integer quantity;

}
