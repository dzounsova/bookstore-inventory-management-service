package com.eli.bims.service;

import com.eli.bims.dto.request.SearchBookRequest;
import com.eli.bims.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookSearchService {

    Page<BookResponse> search(SearchBookRequest searchBookRequest, Pageable pageable);
}
