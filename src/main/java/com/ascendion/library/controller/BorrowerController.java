package com.ascendion.library.controller;

import com.ascendion.library.dto.request.BorrowerRequest;
import com.ascendion.library.dto.response.BorrowerResponse;
import com.ascendion.library.exception.RecordAlreadyExistException;
import com.ascendion.library.service.BorrowerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrower")
@AllArgsConstructor
@Slf4j
public class BorrowerController {

    private  final BorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<BorrowerResponse> createBorrower(@Valid @RequestBody BorrowerRequest borrowerRequest) throws RecordAlreadyExistException {
        log.info("Create borrower request: {}", borrowerRequest);
        return ResponseEntity.ok().body(borrowerService.createBorrower(borrowerRequest));
    }
}
