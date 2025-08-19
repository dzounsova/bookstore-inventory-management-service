package com.eli.bims.service;

import com.eli.bims.dao.Book;
import com.eli.bims.dto.request.CreateBookRequest;
import com.eli.bims.dto.request.UpdateBookRequest;
import com.eli.bims.dto.response.BookResponse;
import com.eli.bims.exception.BookNotFoundException;
import com.eli.bims.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(final ModelMapper modelMapper, final BookRepository bookRepository) {
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponse findBookById(final Long id) {
        Book book = findById(id);
        BookResponse bookResponse = modelMapper.map(book, BookResponse.class);
        log.info("Found book by id={}", bookResponse);

        return bookResponse;
    }

    @Override
    public void create(final CreateBookRequest createBookRequest) {
        log.info("Creating book: {}", createBookRequest);
        Book book = modelMapper.map(createBookRequest, Book.class);
        bookRepository.save(book);
        log.info("Created book: {}", book);
    }

    @Transactional
    @Override
    public void update(final UpdateBookRequest updateBookRequest) {
        log.info("Updating book, id={}", updateBookRequest.getId());
        Book bookFromDB = findById(updateBookRequest.getId());

        if (updateBookRequest.getVersion() != bookFromDB.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(Book.class, bookFromDB.getId());
        }

        modelMapper.map(updateBookRequest, bookFromDB);
        bookRepository.save(bookFromDB);
        log.info("Updated book: {}", bookFromDB);
    }

    @Override
    public void deleteById(final Long id) {
        log.info("Deleting book by id={}", id);
        bookRepository.deleteById(id);
        log.info("Deleted book by id={}", id);
    }

    private Book findById(final Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
    }
}
