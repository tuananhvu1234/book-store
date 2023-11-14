package com.noproject.bookstore.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {
    int page = 0;
    int size = 3;
}
