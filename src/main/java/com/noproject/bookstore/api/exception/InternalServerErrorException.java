package com.noproject.bookstore.api.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() {
        this("");
    }

    public InternalServerErrorException(String msg) {
        super(msg);
    }

}
