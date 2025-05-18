package com.ascendion.library.dto.response;

import com.ascendion.library.entity.Book;

public record BookResponse(Long id, String title, String author, String isbn, boolean isAvailable) {

    public BookResponse(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.isAvailable());
    }
}
