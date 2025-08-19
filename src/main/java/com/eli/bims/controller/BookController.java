package com.eli.bims.controller;

import com.eli.bims.dto.request.CreateBookRequest;
import com.eli.bims.dto.request.SearchBookRequest;
import com.eli.bims.dto.request.UpdateBookRequest;
import com.eli.bims.dto.response.BookResponse;
import com.eli.bims.service.BookSearchService;
import com.eli.bims.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookSearchService bookSearchService;

    public BookController(final BookService bookService, final BookSearchService bookSearchService) {
        this.bookService = bookService;
        this.bookSearchService = bookSearchService;
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable final Long id) {
        log.info("Received get book by id request, id={}", id);
        return bookService.findBookById(id);
    }

    @PostMapping
    public void createBook(@Valid @RequestBody final CreateBookRequest createBookRequest) {
        log.info("Received create book request, book={}", createBookRequest);
        bookService.create(createBookRequest);
    }

    @PutMapping
    public void updateBook(@Valid @RequestBody final UpdateBookRequest updateBookRequest) {
        log.info("Received update book request, id={}", updateBookRequest.getId());
        bookService.update(updateBookRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable final Long id) {
        log.info("Received delete book by id request, id={}", id);
        bookService.deleteById(id);
    }

    @PostMapping("/search")
    public Page<BookResponse> search(@RequestBody final SearchBookRequest searchBookRequest, final Pageable pageable) {
        log.info("Received search request, criteria={}", searchBookRequest);
        return bookSearchService.search(searchBookRequest, pageable);
    }
}
