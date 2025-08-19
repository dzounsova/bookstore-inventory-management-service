package com.eli.bims.service;

import com.eli.bims.config.ModelMapperConfig;
import com.eli.bims.dao.Author;
import com.eli.bims.dao.Book;
import com.eli.bims.dto.request.CreateBookRequest;
import com.eli.bims.dto.request.UpdateBookRequest;
import com.eli.bims.dto.response.BookResponse;
import com.eli.bims.exception.BookNotFoundException;
import com.eli.bims.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;

    ModelMapper modelMapper;

    @InjectMocks
    BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapperConfig().modelMapper();
        ReflectionTestUtils.setField(bookService, "modelMapper", modelMapper);
    }

    @Test
    void findBookById_shouldReturnBookResponse() {
        Long bookId = 100L;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookResponse result = bookService.findBookById(bookId);

        verify(bookRepository).findById(bookId);
        assertThat(result.getId()).isEqualTo(bookId);
    }

    @Test
    void findBookById_shouldThrowExWhenMissing() {
        Long bookId = 100L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findBookById(bookId));
    }

    @Test
    void create_shouldCreateBook() {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Songs of Ice and Fire");
        request.setIsbn("1234");
        request.setGenreId(10L);
        request.setPublisherId(1L);
        request.setAuthorIds(List.of(1L, 2L));

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        Book saved = new Book();
        saved.setId(100L);
        when(bookRepository.save(any(Book.class))).thenReturn(saved);

        bookService.create(request);

        verify(bookRepository).save(captor.capture());
        Book bookToSave = captor.getValue();
        assertEquals("Songs of Ice and Fire", bookToSave.getTitle());
        assertEquals("1234", bookToSave.getIsbn());
        assertEquals(10L, bookToSave.getGenre().getId());
        assertEquals(1L, bookToSave.getPublisher().getId());
        assertEquals(2, bookToSave.getAuthors().size());
        assertThat(bookToSave.getAuthors()).extracting(Author::getId).containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void update_shouldUpdateBook() {
        Long bookId = 100L;
        Book existing = new Book();
        existing.setId(bookId);
        existing.setTitle("Old Title");
        existing.setPrice(BigDecimal.valueOf(99.99));
        existing.setVersion(1L);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existing));

        UpdateBookRequest request = new UpdateBookRequest();
        request.setId(bookId);
        request.setTitle("New Title");
        request.setVersion(1L);

        bookService.update(request);

        verify(bookRepository, never()).save(any(Book.class));
        assertThat(existing.getTitle()).isEqualTo("New Title");
        assertThat(existing.getPrice()).isEqualTo(BigDecimal.valueOf(99.99));
    }

    @Test
    void update_shouldThrowExWhenAlreadyUpdated() {
        Long bookId = 100L;
        Book existing = new Book();
        existing.setId(bookId);
        existing.setTitle("Old Title");
        existing.setPrice(BigDecimal.valueOf(99.99));
        existing.setVersion(2L);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existing));

        UpdateBookRequest request = new UpdateBookRequest();
        request.setId(bookId);
        request.setTitle("New Title");
        request.setVersion(1L);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> bookService.update(request));
    }

    @Test
    void deleteById() {
        Long bookId = 100L;
        bookService.deleteById(bookId);

        verify(bookRepository).deleteById(bookId);
    }
}