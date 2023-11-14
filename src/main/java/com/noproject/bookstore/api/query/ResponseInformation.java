package com.noproject.bookstore.api.query;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * @param httpStatus
 * @param message
 * @param description
 */
public record ResponseInformation(
        HttpStatus httpStatus,
        String message,
        String description
) {

    public ResponseInformation {

        if (Objects.isNull(httpStatus)) {
            throw new NullPointerException();
        }

    }

    public ResponseInformation(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

    public ResponseInformation(HttpStatus httpStatus) {
        this(httpStatus, null, null);
    }

}
