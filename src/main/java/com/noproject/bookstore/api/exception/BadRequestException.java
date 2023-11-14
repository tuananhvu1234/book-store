package com.noproject.bookstore.api.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        this("");
    }

    public BadRequestException(String msg) {
        super(msg);
    }

}
