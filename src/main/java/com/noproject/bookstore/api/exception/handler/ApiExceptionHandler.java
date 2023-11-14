package com.noproject.bookstore.api.exception.handler;

import com.noproject.bookstore.api.exception.BadRequestException;
import com.noproject.bookstore.api.exception.InternalServerErrorException;
import com.noproject.bookstore.api.exception.NotFoundException;
import com.noproject.bookstore.api.query.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleNotFoundException(NotFoundException exception) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND, exception.getMessage(), "Not Found");
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleBadRequestException(BadRequestException exception) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, exception.getMessage(), "Bad Request");
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleInternalServerErrorException(InternalServerErrorException exception) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "Internal Server Error");
    }

}
