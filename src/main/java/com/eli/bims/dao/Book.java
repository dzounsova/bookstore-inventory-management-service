package com.eli.bims.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookSeqGen")
    @SequenceGenerator(name = "bookSeqGen", sequenceName = "book_id_seq", allocationSize = 1)
    private Long id;

    private String isbn;

    private String title;

    private String description;

    private String format;

    private Integer pageCount;

    private String languageCode;

    private String imageUrl;

    private BigDecimal price;

    private Integer quantity;

    private OffsetDateTime publishedDate;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "author",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Author> authors;
}
