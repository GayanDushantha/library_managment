package com.ascendion.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequest(
        @NotBlank String title,
        @NotBlank String author,
        @NotBlank @Size(min = 1, max = 18, message = "length should be in between 1 to 18 characters")
        String isbn) {

}
