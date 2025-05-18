package com.ascendion.library.service;

import com.ascendion.library.constants.BorrowStatus;
import com.ascendion.library.dto.request.BorrowBookRequest;
import com.ascendion.library.dto.request.ReturnBookRequest;
import com.ascendion.library.dto.response.BookResponse;
import com.ascendion.library.dto.response.BorrowBookResponse;
import com.ascendion.library.dto.response.BorrowerResponse;
import com.ascendion.library.dto.response.ReturnBookResponse;
import com.ascendion.library.entity.Book;
import com.ascendion.library.entity.BorrowBook;
import com.ascendion.library.entity.Borrower;
import com.ascendion.library.exception.RecordNotFoundException;
import com.ascendion.library.repository.BookRepository;
import com.ascendion.library.repository.BorrowBookRepository;
import com.ascendion.library.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BorrowBookService {

        private final BookRepository bookRepository;
        private final BorrowerRepository borrowerRepository;
        private final BorrowBookRepository borrowBookRepository;

        @Transactional
        public BorrowBookResponse borrowBook(BorrowBookRequest borrowBookRequest) throws RecordNotFoundException {
            log.info("Borrow book request: {}", borrowBookRequest);

            Optional<Book> bookOpt = bookRepository.findById(borrowBookRequest.bookId());
            if (bookOpt.isPresent() && bookOpt.get().isAvailable()) {

                Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowBookRequest.borrowerId());
                if (borrowerOpt.isPresent()) {

                    Borrower borrower = borrowerOpt.get();

                    // Create Borrowing status
                    BorrowBook borrowBook = BorrowBook.builder()
                            .book(bookOpt.get())
                            .borrower(borrower)
                            .borrowStatus(BorrowStatus.BORROWED)
                            .build();
                    borrowBook = borrowBookRepository.save(borrowBook);

                    // Update Book availability
                    Book book = bookOpt.get();
                    book.setAvailable(false);
                    book = bookRepository.save(book);

                    log.info("Book is borrowed, book Id: {} borrower id: {}", book.getId(), borrower.getId());
                    return new BorrowBookResponse("successfully borrowed",
                            new BookResponse(book), new BorrowerResponse(borrower));

                }else{
                    throw new RecordNotFoundException("No Borrower found with id: "
                            + borrowBookRequest.borrowerId());
                }
            }else{
                throw new RecordNotFoundException("No available Book found with id: "
                        + borrowBookRequest.bookId());
            }
        }

        @Transactional
        public ReturnBookResponse returnBook(ReturnBookRequest returnBookRequest) throws Exception {
            log.info("Return book request: {}", returnBookRequest);

            // Validate book
            Optional<Book> bookOpt = bookRepository.findById(returnBookRequest.bookId());
            if (bookOpt.isPresent()) {

                // check for borrows
                 List<BorrowBook> borrowedBooks = borrowBookRepository.findByBookIdAndBorrowerIdAndBorrowStatus(
                        returnBookRequest.bookId(), returnBookRequest.borrowerId(), BorrowStatus.BORROWED);

                 if (borrowedBooks == null || borrowedBooks.isEmpty()) {
                    throw new IllegalArgumentException("No Borrowed Book found");
                 } else if (borrowedBooks.size() > 1) {
                     throw new Exception("Internal server error");
                 }

                 // Update borrow
                 BorrowBook borrowedBook = borrowedBooks.get(0);
                 borrowedBook.setBorrowStatus(BorrowStatus.RETURNED);
                 borrowBookRepository.save(borrowedBook);

                 // Update book availability
                Book book = bookOpt.get();
                book.setAvailable(true);
                bookRepository.save(book);

                return new ReturnBookResponse("Book Returned Successfully", new BookResponse(book)
                        , new BorrowerResponse(borrowedBook.getBorrower()));
            }else{
                throw new RecordNotFoundException("No Book found with id: "
                        + returnBookRequest.bookId());
            }
        }
}
