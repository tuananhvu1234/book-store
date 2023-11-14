package com.noproject.bookstore.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() {
        this("");
    }

    public InternalServerErrorException(String msg) {
        super(msg);
    }

}
