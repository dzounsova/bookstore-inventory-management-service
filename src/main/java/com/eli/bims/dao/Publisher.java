package com.eli.bims.dao;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisherSeqGen")
    @SequenceGenerator(name = "publisherSeqGen", sequenceName = "publisher_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String description;
}
