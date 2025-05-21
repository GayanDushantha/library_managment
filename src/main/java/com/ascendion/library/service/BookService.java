package com.ascendion.library.service;

import com.ascendion.library.dto.request.BookRequest;
import com.ascendion.library.dto.response.BookListResponse;
import com.ascendion.library.dto.response.BookResponse;
import com.ascendion.library.entity.Book;
import com.ascendion.library.mapper.BookMapper;
import com.ascendion.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse createBook(BookRequest bookRequest) {
        log.info("Creating new book with request: {}", bookRequest);

        try {
            // Validate Title and author with existing book using isbn
            Optional<Book> bookOpt = bookRepository
                    .findFirstByIsbnOrderByCreatedDateDesc(bookRequest.isbn());

            bookOpt.ifPresent(book -> {
                if(!book.getAuthor().equals(bookRequest.author())
                        || !book.getTitle().equals(bookRequest.title())){
                    throw new IllegalArgumentException(
                            "Book title and author is not match with existing book's ISBN Number");
                }
            });

            // Create book Obj
            Book book = BookMapper.toBook(bookRequest);

            // Persist book into db.
            // Assigning response to the same object used to persisted, to align with Mockito.
            book = bookRepository.save(book);

            log.info("Create Book service Finished");

            // convert to BookResponse and return
            return new BookResponse(book);
        } catch (Exception e) {
            log.error("Error creating book for: {}", bookRequest);
            log.error(e.getMessage());
            throw e; // Delegate to Global Exception Handler.
        }
    }

    public BookListResponse getAllBooks() {
        log.info("Get All Books service Started");

        try{
            // Fetching all books
            List<Book> books = bookRepository.findAll();

            // Convert to response object list
            List<BookResponse> bookResponses = books.stream()
                    .map(BookResponse::new)
                    .toList();

            log.info("Get All Books service Finished");
            return new BookListResponse(bookResponses);

        } catch (Exception e) {
            log.error("Error in getting all books");
            throw e; // Delegate to Global Exception Handler.
        }
    }
}
