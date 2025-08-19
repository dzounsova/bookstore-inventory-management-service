package com.eli.bims.dto.response;

import com.eli.bims.dao.BookFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@Builder
public class BookResponse {

    private Long id;

    private String isbn;

    private String title;

    private String description;

    private Integer edition;

    private BookFormat format;

    private Integer pageCount;

    private Locale languageCode;

    private String imageUrl;

    private BigDecimal price;

    private Integer quantity;

    private OffsetDateTime publishedDate;

    private GenreResponse genre;

    private PublisherResponse publisher;

    private List<AuthorResponse> authors;
}
