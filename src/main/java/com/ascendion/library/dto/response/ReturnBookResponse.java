package com.ascendion.library.dto.response;

public record ReturnBookResponse(String status, BookResponse book, BorrowerResponse borrower) {
}
