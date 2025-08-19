package com.eli.bims.dto.request;

import lombok.Data;

@Data
public class SearchBookRequest {

    private String title;

    private Long authorId;

    private String authorName;

    private Long genreId;

    private String genreName;
}
