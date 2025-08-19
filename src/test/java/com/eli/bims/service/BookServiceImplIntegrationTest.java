package com.eli.bims.service;

import com.eli.bims.config.TestBase;
import com.eli.bims.dao.*;
import com.eli.bims.dto.request.CreateBookRequest;
import com.eli.bims.dto.request.UpdateBookRequest;
import com.eli.bims.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceImplIntegrationTest extends TestBase {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Transactional
    @Test
    void create_shouldCreateBook() {
        // prepare
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Songs of Ice and Fire");
        request.setIsbn("1234");
        request.setDescription("A book about the heroes of Ice and Fire");
        request.setEdition(2);
        request.setFormat(BookFormat.HARDCOVER);
        request.setPageCount(100);
        request.setLanguageCode(Locale.ENGLISH);
        request.setQuantity(10);
        request.setPrice(100.99);
        request.setPublishedDate(LocalDate.of(2020, 1, 1));
        request.setGenreId(10L);
        request.setPublisherId(1L);
        request.setAuthorIds(List.of(1L, 2L));

        // do
        bookService.create(request);

        // assert
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
        Book book = books.getFirst();
        assertEquals("Songs of Ice and Fire", book.getTitle());
        assertEquals("1234", book.getIsbn());
        assertEquals("A book about the heroes of Ice and Fire", book.getDescription());
        assertEquals(2, book.getEdition());
        assertEquals(BookFormat.HARDCOVER, book.getFormat());
        assertEquals(100, book.getPageCount());
        assertEquals(Locale.ENGLISH.toLanguageTag(), book.getLanguageCode());
        assertEquals(10, book.getQuantity());
        assertEquals(BigDecimal.valueOf(100.99), book.getPrice());
        assertEquals(LocalDate.of(2020, 1, 1), book.getPublishedDate());
        assertEquals(10L, book.getGenre().getId());
        assertEquals(1L, book.getPublisher().getId());
        assertEquals(2, book.getAuthors().size());
        assertThat(book.getAuthors()).extracting(com.eli.bims.dao.Author::getId).containsExactlyInAnyOrder(1L, 2L);
    }

    @Transactional
    @Test
    void update_shouldUpdateBook() {
        // prepare
        Book book = new Book();
        book.setTitle("Songs of Ice and Fire");
        book.setIsbn("1234");
        book.setDescription("A updatedBook about the heroes of Ice and Fire");
        book.setEdition(2);
        book.setFormat(BookFormat.HARDCOVER);
        book.setPageCount(100);
        book.setLanguageCode("en_GB");
        book.setQuantity(10);
        book.setPrice(BigDecimal.valueOf(100.99));
        book.setPublishedDate(LocalDate.of(2020, 1, 1));
        Genre genre = new Genre();
        genre.setId(10L);
        book.setGenre(genre);
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        book.setPublisher(publisher);
        Author author1 = new Author();
        author1.setId(1L);
        Author author2 = new Author();
        author2.setId(2L);
        book.setAuthors(List.of(author1, author2));

        bookRepository.save(book);

        UpdateBookRequest updateRequest = new UpdateBookRequest();
        updateRequest.setId(1L);
        updateRequest.setTitle("Songs of Ice and Fire, Book 1");
        updateRequest.setVersion(0L);

        // do
        bookService.update(updateRequest);

        // assert
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
        Book updatedBook = books.getFirst();
        assertEquals("Songs of Ice and Fire, Book 1", updatedBook.getTitle());
        assertEquals("1234", updatedBook.getIsbn());
        assertEquals("A book about the heroes of Ice and Fire", updatedBook.getDescription());
        assertEquals(2, updatedBook.getEdition());
        assertEquals(BookFormat.HARDCOVER, updatedBook.getFormat());
        assertEquals(100, updatedBook.getPageCount());
        assertEquals("en_GB", updatedBook.getLanguageCode());
        assertEquals(10, updatedBook.getQuantity());
        assertEquals(BigDecimal.valueOf(100.99), updatedBook.getPrice());
        assertEquals(LocalDate.of(2020, 1, 1), updatedBook.getPublishedDate());
        assertEquals(10L, updatedBook.getGenre().getId());
        assertEquals(1L, updatedBook.getPublisher().getId());
        assertEquals(2, updatedBook.getAuthors().size());
        assertThat(updatedBook.getAuthors()).extracting(com.eli.bims.dao.Author::getId).containsExactlyInAnyOrder(1L, 2L);
        assertEquals(1, updatedBook.getVersion());
    }

    @Test
    void deleteById_shouldDeleteBook() {
        // prepare
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Songs of Ice and Fire");
        request.setIsbn("1234");
        request.setDescription("A book about the heroes of Ice and Fire");
        request.setEdition(2);
        request.setFormat(BookFormat.HARDCOVER);
        request.setPageCount(100);
        request.setLanguageCode(Locale.ENGLISH);
        request.setQuantity(10);
        request.setPrice(100.99);
        request.setPublishedDate(LocalDate.of(2020, 1, 1));
        request.setGenreId(10L);
        request.setPublisherId(1L);
        List<Long> authorIds = new ArrayList<>();
        authorIds.add(1L);
        authorIds.add(2L);
        request.setAuthorIds(authorIds);

        bookService.create(request);
        List<Book> createdBooks = bookRepository.findAll();
        assertEquals(1, createdBooks.size());
        Book savedBook = createdBooks.getFirst();

        // do
        bookService.deleteById(savedBook.getId());

        List<Book> result = bookRepository.findAll();
        assertThat(result).isEmpty();
    }
}
