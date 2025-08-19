package com.eli.bims.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

// Assuming the user cannot update all book fields,
// in case of all fields update, we can have UpsertBookRequest replacing CreateBookRequest and UpdateBookRequest.
@Data
public class UpdateBookRequest {

    @NotNull
    private Long id;

    private String title;

    private String description;

    @Positive
    private Integer pageCount;

    private String imageUrl;

    @Positive
    private Double price;

    @PositiveOrZero
    private Integer quantity;

    @NotNull
    private long version;
}
