package com.noproject.bookstore.controller;

import com.noproject.bookstore.api.query.ApiRequest;
import com.noproject.bookstore.api.query.ApiResponse;
import com.noproject.bookstore.api.query.ResponseInformation;
import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.mapper.Mapper;
import com.noproject.bookstore.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/author")
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final Mapper<Author, AuthorDTO> authorDTOMapper;

    public AuthorController(
            AuthorRepository authorRepository,
            Mapper<Author, AuthorDTO> authorDTOMapper
    ) {
        this.authorRepository = authorRepository;
        this.authorDTOMapper = authorDTOMapper;
    }

    @GetMapping({"", "/"})
    public ApiResponse<List<?>> index() {
        ApiRequest<AuthorDTO> request = new ApiRequest<>();

        request.setQuery("penName", "id");

        return request.getResponseArray(
                new ResponseInformation(HttpStatus.OK),
                authorDTOMapper.map(
                        authorRepository.findAll(),
                        new ArrayList<>() {
                        }
                )
        );
    }

}
