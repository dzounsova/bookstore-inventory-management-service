package com.eli.bims.dto.response;

import lombok.Builder;

@Builder
public class GenreResponse {

    private Long id;

    private String name;

    private String description;
}
