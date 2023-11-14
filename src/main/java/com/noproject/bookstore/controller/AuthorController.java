package com.noproject.bookstore.controller;

import com.noproject.bookstore.api.query.ApiRequest;
import com.noproject.bookstore.api.query.ApiResponse;
import com.noproject.bookstore.api.query.ResponseInformation;
import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.entity.Author;
import com.noproject.bookstore.mapper.Mapper;
import com.noproject.bookstore.repository.AuthorRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/test")
    public void test() {
        Optional<Author> optional = authorRepository.findById(1L);

        if (optional.isPresent()) {
            Author author = optional.get();

            Example<Author> example;
            try {
                example = Example.of(author);
                authorRepository.exists(example);
            } catch (InvalidDataAccessApiUsageException e) {
                Field[] fields = author.getClass().getDeclaredFields();

                ArrayList<String> ignorePaths = new ArrayList<>();
                for (Field field : fields) {
                    if (field.getType().getPackageName().equals(author.getClass().getPackageName())) {
                        System.out.println(field);
                        Field[] fields1 = field.getType().getDeclaredFields();
                        for (Field field1 : fields1) {
                            System.out.println(field1);
                            if (field1.getType().equals(author.getClass())) {
                                ignorePaths.add(field.getName());
                                break;
                            }
                        }
                    }
                }
                example = Example.of(author,
                        ExampleMatcher.matching().withIgnorePaths(
                                ignorePaths.toArray(String[]::new)
                        )
                );
                authorRepository.exists(example);
            }

//
//            boolean isExist = authorRepository.exists(Example.of(author, ExampleMatcher.matching().withIgnorePaths(
//                    "user"
//            )));

//            System.out.println(isExist);
        }

    }

}
