package com.noproject.bookstore.configuration;

import com.noproject.bookstore.api.query.HandlerCollection;
import com.noproject.bookstore.api.query.RequestHandler;
import com.noproject.bookstore.dto.AuthorDTO;
import com.noproject.bookstore.dto.BookDTO;
import com.noproject.bookstore.dto.CategoryDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class RequestHandlerConfiguration {

    static {

        /*
            Config for AuthorDTO
         */
        HandlerCollection authorDTOHandlers = RequestHandler.add(
                AuthorDTO.class, new HandlerCollection()
        );
        authorDTOHandlers.addHandler("id", AuthorDTO::getId);
        authorDTOHandlers.addHandler("penName", AuthorDTO::getPenName);

        /*
            Config for BookDTO
         */
        HandlerCollection bookDTOHandlers = RequestHandler.add(
                BookDTO.class, new HandlerCollection()
        );
        bookDTOHandlers.addHandler("id", BookDTO::getId);
        bookDTOHandlers.addHandler("title", BookDTO::getTitle);
        bookDTOHandlers.addHandler("shortDescription", BookDTO::getShortDescription);
        bookDTOHandlers.addHandler("fullDescription", BookDTO::getFullDescription);
        bookDTOHandlers.addHandler("categories", BookDTO::getCategories);
        bookDTOHandlers.addHandler("author", BookDTO::getAuthor);
        bookDTOHandlers.addHandler("image", BookDTO::getImage);
        bookDTOHandlers.addHandler("price", BookDTO::getPrice);
        bookDTOHandlers.addHandler("quantity", BookDTO::getQuantity);

        /*
         Config for CategoryDTO
         */
        HandlerCollection categoryDTOHandlers = RequestHandler.add(
                CategoryDTO.class, new HandlerCollection()
        );

        categoryDTOHandlers.addHandler("id", CategoryDTO::getId);
        categoryDTOHandlers.addHandler("name", CategoryDTO::getName);
        categoryDTOHandlers.addHandler("description", CategoryDTO::getDescription);


    }

}
