package com.ascendion.library;

import com.ascendion.library.dto.request.BorrowerRequest;
import com.ascendion.library.dto.response.BorrowerResponse;
import com.ascendion.library.entity.Borrower;
import com.ascendion.library.repository.BorrowerRepository;
import com.ascendion.library.service.BorrowerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class TestBorrowerService {

    @Mock
    BorrowerRepository borrowerRepository;

    @InjectMocks
    BorrowerService borrowerService;

    @Test
    void shouldCreateBorrowerSaveBorrowerSuccessfully() {
        log.info("Evaluate Test shouldCreateBorrowerSaveBorrowerSuccessfully");

        // Preparing Data
        BorrowerRequest borrowerRequest = new BorrowerRequest("John", "john@gmail.com");

        // Mocking Objects
        when(borrowerRepository.save(any(Borrower.class))).thenAnswer(invocation -> {
            Borrower input = invocation.getArgument(0);
            return Borrower.builder()
                    .id(10L)
                    .name(input.getName())
                    .email(input.getEmail())
                    .build();
        });

        // Calling actual methods
        BorrowerResponse response = borrowerService.createBorrower(borrowerRequest);

        // Test BorrowerResponse
        assertNotNull(response);
        assertEquals(borrowerRequest.name(), response.name());
        assertEquals(borrowerRequest.email(), response.email());
        assertEquals(10L, response.id()); // assuming BorrowerResponse has an id()

        // Verify save was called
        verify(borrowerRepository, times(1)).save(any(Borrower.class));

        log.info("Test passed: shouldCreateBorrowerSaveBorrowerSuccessfully");
    }

}
