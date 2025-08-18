package com.eli.bims.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorSeqGen")
    @SequenceGenerator(name = "authorSeqGen", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;

    private String firstName;

    private String lastName;

    private String isni;

    private String viaf;

    private String imageUrl;

    private String description;

    @ManyToMany(mappedBy = "authors")
    List<Book> books;
}
