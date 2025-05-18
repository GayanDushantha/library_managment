package com.ascendion.library.dto.response;

import com.ascendion.library.entity.Book;
import com.ascendion.library.entity.Borrower;

public record BorrowBookResponse(String status, BookResponse book, BorrowerResponse borrower) {
}
