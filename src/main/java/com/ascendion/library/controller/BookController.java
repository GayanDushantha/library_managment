package com.ascendion.library.controller;

import com.ascendion.library.dto.request.BookRequest;
import com.ascendion.library.dto.response.BookListResponse;
import com.ascendion.library.dto.response.BookResponse;
import com.ascendion.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest bookRequest) {
        log.info("Create book request: {}", bookRequest);

        return ResponseEntity.ok(bookService.createBook(bookRequest));

    }

    @GetMapping
    public ResponseEntity<BookListResponse> getAllBooks() {
        log.info("Get All Books web Started");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

}
