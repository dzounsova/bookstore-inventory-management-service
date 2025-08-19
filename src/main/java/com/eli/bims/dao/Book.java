package com.eli.bims.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private Integer edition;

    @Enumerated(EnumType.STRING)
    private BookFormat format;

    private Integer pageCount;

    private String languageCode;

    private String imageUrl;

    private BigDecimal price;

    private Integer quantity;

    private LocalDate publishedDate;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private List<Author> authors;
}
