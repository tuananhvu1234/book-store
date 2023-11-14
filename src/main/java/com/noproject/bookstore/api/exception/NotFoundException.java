package com.noproject.bookstore.api.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("Record does not exist.");
    }

    public NotFoundException(String msg) {
        super(msg);
    }

}
