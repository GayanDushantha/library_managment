package com.ascendion.library.service;

import com.ascendion.library.dto.request.BorrowerRequest;
import com.ascendion.library.dto.response.BorrowerResponse;
import com.ascendion.library.entity.Borrower;
import com.ascendion.library.repository.BorrowerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerResponse createBorrower(BorrowerRequest borrowerRequest) {
        log.info("Creating new borrower with request: {}", borrowerRequest);

        // Create Entity
        Borrower borrower = Borrower.builder()
                .name(borrowerRequest.name())
                .email(borrowerRequest.email())
                .build();

        // Persist into db
        borrower = borrowerRepository.save(borrower);

        log.info("Create Borrower service Finished");
        return new BorrowerResponse(borrower);
    }

}
