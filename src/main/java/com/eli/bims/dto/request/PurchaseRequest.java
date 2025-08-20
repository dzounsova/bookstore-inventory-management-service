package com.eli.bims.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PurchaseRequest {

    @NotNull
    private Long bookId;

    @NotNull
    @Positive
    private Integer quantity;

    // other data about purchase e.g., receipt id, buyer, seller, etc.
}
