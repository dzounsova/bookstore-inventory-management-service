package com.eli.bims.service;

import com.eli.bims.dto.request.CreateBookRequest;
import com.eli.bims.dto.request.UpdateBookRequest;
import com.eli.bims.dto.response.BookResponse;

public interface BookService {

    BookResponse findBookById(Long id);

    void create(CreateBookRequest createBookRequest);

    void update(UpdateBookRequest updateBookRequest);

    void deleteById(Long id);
}
