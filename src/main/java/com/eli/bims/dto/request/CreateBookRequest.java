package com.eli.bims.dto.request;

import com.eli.bims.converter.LocaleDeserializer;
import com.eli.bims.dao.BookFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Data
public class CreateBookRequest {

    @NotNull
    private String isbn;

    @NotNull
    private String title;

    private String description;

    private Integer edition;

    @NotNull
    private BookFormat format;

    @NotNull
    @Positive
    private Integer pageCount;

    @NotNull
    @JsonDeserialize(using = LocaleDeserializer.class)
    private Locale languageCode;

    private String imageUrl;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull
    private LocalDate publishedDate;

    @NotNull
    private Long genreId;

    @NotNull
    private Long publisherId;

    @NotEmpty
    private List<Long> authorIds;
}
