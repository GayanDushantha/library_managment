package com.ascendion.library;

import com.ascendion.library.constants.BorrowStatus;
import com.ascendion.library.dto.request.BorrowBookRequest;
import com.ascendion.library.dto.request.ReturnBookRequest;
import com.ascendion.library.dto.response.BorrowBookResponse;
import com.ascendion.library.dto.response.ReturnBookResponse;
import com.ascendion.library.entity.Book;
import com.ascendion.library.entity.BorrowBook;
import com.ascendion.library.entity.Borrower;
import com.ascendion.library.exception.RecordNotFoundException;
import com.ascendion.library.repository.BookRepository;
import com.ascendion.library.repository.BorrowBookRepository;
import com.ascendion.library.repository.BorrowerRepository;
import com.ascendion.library.service.BorrowBookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class TestBorrowBookService {

    @Mock
    BookRepository bookRepository;
    @Mock
    BorrowerRepository borrowerRepository;
    @Mock
    BorrowBookRepository borrowBookRepository;

    @InjectMocks
    BorrowBookService borrowBookService;

    /**+
     * Test happy path for borrowBook method
     * @throws RecordNotFoundException -
     */
    @Test
    void borrowBookShouldSucceedWhenBookAndBorrowerExistAndBookIsAvailable() throws RecordNotFoundException {
        log.info("Evaluating Test: borrowBookShouldSucceedWhenBookAndBorrowerExistAndBookIsAvailable");

        // Prepare Data
        Book book = Book.builder()
                .id(1L).title("Title")
                .author("Author").isbn("123").isAvailable(true)
                .build();

        Borrower borrower = Borrower.builder()
                .id(2L).name("John")
                .email("john@gmail.com")
                .build();
        BorrowBookRequest request = new BorrowBookRequest(1L, 2L);

        // Mocking Objects
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(2L)).thenReturn(Optional.of(borrower));
        when(borrowBookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Calling actual method
        BorrowBookResponse response = borrowBookService.borrowBook(request);

        // Testng response
        assertNotNull(response);
        assertEquals("successfully borrowed", response.status());
        assertFalse(response.book().isAvailable());
        assertEquals(borrower.getName(), response.borrower().name());
        assertEquals(borrower.getEmail(), response.borrower().email());

        log.info("Test passed: borrowBookShouldSucceedWhenBookAndBookIsAvailable");
    }

    /**+
     * Test exceptional behaviour for borrowBook method
     */
    @Test
    void borrowBookShouldThrowWhenBookIsNotAvailable() {
        log.info("Evaluating Test: borrowBookShouldThrowWhenBookIsNotAvailable");

        // Prepare Data
        Book book = Book.builder()
                .id(1L).
                isAvailable(false)
                .build();
        BorrowBookRequest request = new BorrowBookRequest(1L, 2L);

        // mocking Objects
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Calling actual methods and Testing
        assertThrows(RecordNotFoundException.class, () -> borrowBookService.borrowBook(request));
        log.info("Test passed: borrowBookShouldThrowWhenBookIsNotAvailable");
    }

    /**+
     * Test exceptional behaviour for borrowBook method
     */
    @Test
    void borrowBookShouldThrowWhenBorrowerNotFound() {
        log.info("Evaluating Test: borrowBookShouldThrowWhenBorrowerNotFound");

        // Preparing data
        Book book = Book.builder()
                .id(1L)
                .isAvailable(true)
                .build();
        BorrowBookRequest request = new BorrowBookRequest(1L, 2L);

        // Mocking Objects
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(2L)).thenReturn(Optional.empty());

        // Calling actual methods and Testing
        assertThrows(RecordNotFoundException.class, () -> borrowBookService.borrowBook(request));

        log.info("Test passed: borrowBookShouldThrowWhenBorrowerNotFound");
    }

    /**+
     * Testing Happy path for Return book
     * @throws Exception
     */
    @Test
    void returnBookShouldSucceedWhenBorrowExists() throws Exception {
        log.info("Evaluating Test: returnBookShouldSucceedWhenBorrowExists");

        // Preparing data
        Book book = Book.builder()
                .id(1L)
                .isAvailable(false)
                .build();

        Borrower borrower = Borrower.builder()
                .id(2L).name("John").email("john@example.com")
                .build();

        BorrowBook borrowedBook = BorrowBook.builder()
                .book(book).borrower(borrower)
                .borrowStatus(BorrowStatus.BORROWED)
                .build();
        ReturnBookRequest request = new ReturnBookRequest(1L, 2L);

        // Mocking Objects
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowBookRepository.findByBookIdAndBorrowerIdAndBorrowStatus(1L, 2L, BorrowStatus.BORROWED))
                .thenReturn(List.of(borrowedBook));
        when(borrowBookRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(bookRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // Calling actual method
        ReturnBookResponse response = borrowBookService.returnBook(request);

        // Testing response
        assertNotNull(response);
        assertEquals("Book Returned Successfully", response.status());
        assertTrue(response.book().isAvailable());

        log.info("Test passed: returnBookShouldSucceedWhenBorrowExists");
    }

    /**+
     * Test exceptional behaviour for returnBook method
     */
    @Test
    void returnBookShouldThrowWhenBookNotFound() {
        log.info("Evaluating Test: returnBookShouldThrowWhenBookNotFound");

        // Preparing Data
        ReturnBookRequest request = new ReturnBookRequest(1L, 2L);

        // Mocking Objects
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Calling Actual method and Testing
        assertThrows(RecordNotFoundException.class, () -> borrowBookService.returnBook(request));
        log.info("Test passed: returnBookShouldThrowWhenBookNotFound");
    }


    /**+
     * Test exceptional behaviour for returnBook method
     */
    @Test
    void returnBookShouldThrowWhenNoBorrowedRecordFound() {
        log.info("Evaluating Test: returnBookShouldThrowWhenNoBorrowedRecordFound");

        // Preparing Data
        Book book = Book.builder().id(1L).isAvailable(false).build();
        ReturnBookRequest request = new ReturnBookRequest(1L, 2L);

        // Mocking Objects
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowBookRepository.findByBookIdAndBorrowerIdAndBorrowStatus(1L, 2L, BorrowStatus.BORROWED))
                .thenReturn(Collections.emptyList());

        // Calling actual method and Testing
        assertThrows(IllegalArgumentException.class, () -> borrowBookService.returnBook(request));
        log.info("Test passed: returnBookShouldThrowWhenNoBorrowedRecordFound");
    }

    /**+
     * Test exceptional behaviour for returnBook method
     */
    @Test
    void returnBookShouldThrowWhenMultipleBorrowedRecordsFound() {
        log.info("Evaluating Test: returnBookShouldThrowWhenMultipleBorrowedRecordsFound");

        // Preparing Data
        Book book = Book.builder()
                .id(1L).isAvailable(false)
                .build();

        BorrowBook bb1 = new BorrowBook(), bb2 = new BorrowBook();
        ReturnBookRequest request = new ReturnBookRequest(1L, 2L);

        // Mocking Objects
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowBookRepository.findByBookIdAndBorrowerIdAndBorrowStatus(1L, 2L, BorrowStatus.BORROWED))
                .thenReturn( List.of(bb1, bb2));

        // Calling actual methods and Testing
        assertThrows(Exception.class, () -> borrowBookService.returnBook(request));
        log.info("Test passed: returnBookShouldThrowWhenMultipleBorrowedRecordsFound");
    }

}
