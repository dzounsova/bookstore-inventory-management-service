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

    @NotNull
    private String title;

    private String description;

    private Integer pageCount;

    private String imageUrl;

    @Positive
    private Double price;

    @PositiveOrZero
    private Integer quantity;
}
