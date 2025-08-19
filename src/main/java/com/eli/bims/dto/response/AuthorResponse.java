package com.eli.bims.dto.response;

import lombok.Builder;

@Builder
public class AuthorResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private String description;
}
