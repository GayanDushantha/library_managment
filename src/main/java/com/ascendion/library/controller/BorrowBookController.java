package com.ascendion.library.controller;

import com.ascendion.library.dto.request.BorrowBookRequest;
import com.ascendion.library.dto.request.ReturnBookRequest;
import com.ascendion.library.dto.response.BorrowBookResponse;
import com.ascendion.library.dto.response.ReturnBookResponse;
import com.ascendion.library.exception.RecordNotFoundException;
import com.ascendion.library.service.BorrowBookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrowBook")
@Slf4j
@AllArgsConstructor
public class BorrowBookController {

    private final BorrowBookService borrowBookService;

    @PostMapping("/borrow")
    public ResponseEntity<BorrowBookResponse> borrowBook(@Valid @RequestBody BorrowBookRequest borrowBookRequest)
            throws RecordNotFoundException {
        log.info("Borrow book request: {}", borrowBookRequest);

        return ResponseEntity.ok().body(borrowBookService.borrowBook(borrowBookRequest));
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnBookResponse> returnBook(@Valid @RequestBody ReturnBookRequest returnBookRequest)
            throws Exception {
        log.info("Return book request: {}", returnBookRequest);

        return ResponseEntity.ok().body(borrowBookService.returnBook(returnBookRequest));
    }
}
