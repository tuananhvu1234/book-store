package com.noproject.bookstore.api.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {

    /**
     *
     */
    private final Integer statusCode;

    /**
     *
     */
    private String message;

    /**
     *
     */
    private String errorMessage;

    /**
     * data payload
     */
    private T data;

    /**
     *
     */
    private final String description;


//  @JsonAnyGetter
//  private Map<String, Object> otherProps;

    /**
     * @param information the info response.
     * @param data        data payload.
     */
    public ApiResponse(ResponseInformation information, T data) {
        this(information.httpStatus(), information.message(), information.description(), data);
    }

    public ApiResponse(ResponseInformation information) {
        this(information, null);
    }

    public ApiResponse(HttpStatus httpStatus, String message, String description, T data) {

        if (Objects.isNull(httpStatus)) {
            throw new NullPointerException("httpStatus must be non-null");
        }

        this.statusCode = httpStatus.value();

        if (httpStatus.isError()) {
            this.errorMessage = message;
        } else {
            this.message = message;
            this.data = data;
        }

        this.description = description;

    }

    public ApiResponse(HttpStatus httpStatus, String message, String description) {
        this(httpStatus, message, null, null);
    }

    public ApiResponse(HttpStatus httpStatus, String message, T data) {
        this(httpStatus, message, null, data);
    }

    public ApiResponse(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null, null);
    }

    public ApiResponse(HttpStatus httpStatus, T data) {
        this(httpStatus, null, data);
    }

    public ApiResponse(HttpStatus httpStatus) {
        this(httpStatus, null, null, null);
    }

}
