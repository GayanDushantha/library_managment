package com.ascendion.library.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BorrowBookRequest(
        @Positive( message = "bookId should be grater than 0") Long bookId,
        @Positive( message = "borrowerId should be grater than 0") Long borrowerId) {
}
