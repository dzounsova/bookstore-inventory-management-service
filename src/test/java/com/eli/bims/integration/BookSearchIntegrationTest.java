package com.eli.bims.integration;

import com.eli.bims.config.TestBase;
import com.eli.bims.dto.request.SearchBookRequest;
import com.eli.bims.dto.response.BookResponse;
import com.eli.bims.repository.BookRepository;
import com.eli.bims.service.BookSearchService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookSearchIntegrationTest extends TestBase {

    @Autowired
    private BookSearchService bookSearchService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    void setUpAll() {
        bookRepository.saveAll(getMockBooks());
    }

    @AfterAll
    void tearDownAll() {
        bookRepository.deleteAll();
        bookRepository.flush();
    }

    @Transactional
    @Test
    void search_shouldSearchBookByCriteriaTitle() {
        // prepare
        String title = "Fire";
        SearchBookRequest request = new SearchBookRequest();
        request.setTitle(title);

        // do
        Page<BookResponse> result = bookSearchService.search(request, PageRequest.of(0, 10));

        // assert
        assertNotNull(result);
        List<BookResponse> books = result.getContent();
        assertEquals(2, books.size());
        assertThat(books).extracting(BookResponse::getTitle).containsExactlyInAnyOrder(
                "Songs of Ice and Fire, Book 1", "Songs of Ice and Fire, Book 2");
    }

    @Transactional
    @Test
    void search_shouldSearchBookByCriteriaGenreId() {
        // prepare
        Long genreId = 6L;
        SearchBookRequest request = new SearchBookRequest();
        request.setGenreId(genreId);

        // do
        Page<BookResponse> result = bookSearchService.search(request, PageRequest.of(0, 10));

        // assert
        assertNotNull(result);
        List<BookResponse> books = result.getContent();
        assertEquals(2, books.size());
        assertThat(books).extracting(BookResponse::getTitle).containsExactlyInAnyOrder(
                "Songs of Ice and Fire, Book 1", "Songs of Ice and Fire, Book 2");
        assertThat(books).extracting(b -> b.getGenre().getId()).allMatch(id -> id.equals(genreId));
    }

    @Transactional
    @Test
    void search_shouldSearchBookByCriteriaGenreName() {
        // prepare
        String genreName = "computers";
        SearchBookRequest request = new SearchBookRequest();
        request.setGenreName(genreName);

        // do
        Page<BookResponse> result = bookSearchService.search(request, PageRequest.of(0, 10));

        // assert
        assertNotNull(result);
        List<BookResponse> books = result.getContent();
        assertEquals(1, books.size());
        BookResponse book = books.getFirst();
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Computers & Technology", book.getGenre().getName());
    }

    @Transactional
    @Test
    void search_shouldSearchBookByCriteriaAuthorId() {
        // prepare
        Long authorId = 2L;
        SearchBookRequest request = new SearchBookRequest();
        request.setAuthorId(authorId);

        // do
        Page<BookResponse> result = bookSearchService.search(request, PageRequest.of(0, 10));

        // assert
        assertNotNull(result);
        List<BookResponse> books = result.getContent();
        assertEquals(3, books.size());
        assertThat(books).extracting(BookResponse::getTitle).containsExactlyInAnyOrder(
                "Songs of Ice and Fire, Book 1",
                "Songs of Ice and Fire, Book 2",
                "Multiple authors book"
        );
    }

    @Transactional
    @Test
    void search_shouldSearchBookByCriteriaAuthorName() {
        // prepare
        String authorName = "martin";
        SearchBookRequest request = new SearchBookRequest();
        request.setAuthorName(authorName);

        // do
        Page<BookResponse> result = bookSearchService.search(request, PageRequest.of(0, 10));

        // assert
        assertNotNull(result);
        List<BookResponse> books = result.getContent();
        assertEquals(4, books.size());
        assertThat(books).extracting(BookResponse::getTitle).containsExactlyInAnyOrder(
                "Songs of Ice and Fire, Book 1",
                "Songs of Ice and Fire, Book 2",
                "Multiple authors book",
                "Clean Code");
    }

    @Transactional
    @Test
    void search_shouldSearchBookByMultiCriteria() {
        // prepare
        String authorName = "martin";
        String genreName = "computers";
        SearchBookRequest request = new SearchBookRequest();
        request.setAuthorName(authorName);
        request.setGenreName(genreName);

        // do
        Page<BookResponse> result = bookSearchService.search(request, PageRequest.of(0, 10));

        // assert
        assertNotNull(result);
        List<BookResponse> books = result.getContent();
        assertEquals(1, books.size());
        BookResponse book = books.getFirst();
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Computers & Technology", book.getGenre().getName());
    }
}
