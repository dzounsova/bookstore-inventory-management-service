package com.eli.bims.service;

import com.eli.bims.config.ModelMapperConfig;
import com.eli.bims.dto.request.SearchBookRequest;
import com.eli.bims.dto.response.BookResponse;
import com.eli.bims.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceImplTest {

    @Mock
    BookRepository bookRepository;

    ModelMapper modelMapper;

    @InjectMocks
    BookSearchServiceImpl bookSearchService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapperConfig().modelMapper();
        ReflectionTestUtils.setField(bookSearchService, "modelMapper", modelMapper);
    }

    @Test
    void search_shouldReturnPageOfBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(Page.empty(pageable));

        SearchBookRequest request = new SearchBookRequest();
        request.setTitle("title");
        Page<BookResponse> response = bookSearchService.search(request, pageable);

        assertThat(response.getContent()).isEmpty();
        verify(bookRepository).findAll(any(Specification.class), eq(pageable));
    }
}