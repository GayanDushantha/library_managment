package com.ascendion.library.service;

import com.ascendion.library.dto.request.BorrowerRequest;
import com.ascendion.library.dto.response.BorrowerResponse;
import com.ascendion.library.entity.Borrower;
import com.ascendion.library.exception.RecordAlreadyExistException;
import com.ascendion.library.mapper.BorrowerMapper;
import com.ascendion.library.repository.BorrowerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerResponse createBorrower(BorrowerRequest borrowerRequest) throws RecordAlreadyExistException {
        log.info("Creating new borrower with request: {}", borrowerRequest);

        try{

            // Check if already exist by email.
            if( borrowerRepository.existsByEmail(borrowerRequest.email())){
                throw new RecordAlreadyExistException("Borrower already exists");
            }

            // Create Entity
            Borrower borrower = BorrowerMapper.toBorrower(borrowerRequest);

            // Persist into db
            borrower = borrowerRepository.save(borrower);

            log.info("Create Borrower service Finished");
            return new BorrowerResponse(borrower);

        } catch (Exception e) {
            log.error("Error in creating new borrower for request: {}", borrowerRequest,e);
            throw e; // Delegate to Global Exception Handler.
        }
    }
}
