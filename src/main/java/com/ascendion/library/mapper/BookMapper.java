package com.ascendion.library.mapper;

import com.ascendion.library.dto.request.BookRequest;
import com.ascendion.library.entity.Book;

public class BookMapper {

    public static Book toBook(BookRequest bookRequest) {
        return Book.builder()
                .isbn(bookRequest.isbn())
                .title(bookRequest.title())
                .author(bookRequest.author())
                .isAvailable(true)
                .build();
    }
}
