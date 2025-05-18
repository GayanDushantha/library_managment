package com.ascendion.library;

import com.ascendion.library.dto.request.BookRequest;
import com.ascendion.library.dto.response.BookListResponse;
import com.ascendion.library.dto.response.BookResponse;
import com.ascendion.library.entity.Book;
import com.ascendion.library.repository.BookRepository;
import com.ascendion.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class TestBookService {

    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookService bookService;

    @Test
    void addBookShouldAddNewBookSuccessfully() {
        log.info("Evaluating Test addBookShouldAddNewBookSuccessfully");

        // Preparing data
        long bookId = 1L;
        BookRequest bookRequest =
                new BookRequest("Flowing River", "David", "isbn1");

        // mocking objects
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book input = invocation.getArgument(0);
            return Book.builder()
                    .id(bookId) // simulate DB-assigned ID
                    .title(input.getTitle())
                    .author(input.getAuthor())
                    .isbn(input.getIsbn())
                    .isAvailable(input.isAvailable())
                    .build();
        });

        // Calling the actual methods.
        BookResponse response = bookService.createBook(bookRequest);

        // Test Book Response
        assertNotNull(response);
        assertEquals(bookId, response.id());
        assertEquals(bookRequest.title(), response.title());
        assertEquals(bookRequest.author(), response.author());
        assertEquals(bookRequest.isbn(), response.isbn());
        assertTrue(response.isAvailable());
        verify(bookRepository, times(1)).save(any(Book.class));

        log.info("Test Passed: addBookShouldAddNewBookSuccessfully");
    }

    @Test
    void getAllBooksShouldReturnAllBooksSuccessfully() {
        log.info("Evaluating Test GetAllBooksShouldReturnAllBooksSuccessfully");

        // Preparing data
        List<Book> books = Arrays.asList(new Book[]{
                Book.builder()
                        .id(11L).
                        title("Book One").
                        author("Author A").
                        isbn("111").
                        isAvailable(true).
                        build(),
                Book.builder()
                        .id(12L).
                        title("Book Two").
                        author("Author B").
                        isbn("222").
                        isAvailable(false).
                        build(),
                Book.builder()
                        .id(13L).
                        title("Book Three").
                        author("Author C").
                        isbn("333").
                        isAvailable(true).
                        build(),
                });

        // Mocking Objects
        when(bookRepository.findAll()).thenReturn(books);

        // Calling actual methods.
        BookListResponse booksResponse = bookService.getAllBooks();

        //Testing Response
        assertNotNull(booksResponse);
        assertEquals(3, booksResponse.books().size());

        BookResponse book1 = booksResponse.books().get(0);
        assertEquals(11L, book1.id());
        assertEquals("Book One", book1.title());
        assertEquals("Author A", book1.author());
        assertEquals("111", book1.isbn());
        assertTrue(book1.isAvailable());

        BookResponse book2 = booksResponse.books().get(1);
        assertEquals(12L, book2.id());
        assertEquals("Book Two", book2.title());
        assertEquals("Author B", book2.author());
        assertEquals("222", book2.isbn());
        assertFalse(book2.isAvailable());

        BookResponse book3 = booksResponse.books().get(2);
        assertEquals(13L, book3.id());
        assertEquals("Book Three", book3.title());
        assertEquals("Author C", book3.author());
        assertEquals("333", book3.isbn());
        assertTrue(book3.isAvailable());

        // Verify number of calls
        verify(bookRepository, times(1)).findAll();

        log.info("Test Passed: getAllBooksShouldReturnAllBooksSuccessfully");
    }
}
