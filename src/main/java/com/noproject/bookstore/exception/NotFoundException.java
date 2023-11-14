package com.noproject.bookstore.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("Record does not exist.");
    }

    public NotFoundException(String msg) {
        super(msg);
    }

}
