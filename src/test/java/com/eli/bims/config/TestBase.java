package com.eli.bims.config;

import com.eli.bims.dao.*;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBase {

    static protected final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.2"));
        postgres.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration,classpath:db/dev");
    }

    protected List<Book> getMockBooks() {
        Author author1 = new Author();
        author1.setId(1L);
        Author author2 = new Author();
        author2.setId(2L);
        Author author3 = new Author();
        author3.setId(3L);
        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);
        Publisher publisher2 = new Publisher();
        publisher2.setId(2L);
        Publisher publisher3 = new Publisher();
        publisher3.setId(3L);
        Genre genre1 = new Genre();
        genre1.setId(6L);
        Genre genre2 = new Genre();
        genre2.setId(11L);
        Genre genre3 = new Genre();
        genre3.setId(3L);

        Book book1 = new Book();
        book1.setTitle("Songs of Ice and Fire, Book 1");
        book1.setIsbn("1234");
        book1.setDescription("A book about the heroes of Ice and Fire");
        book1.setEdition(2);
        book1.setFormat(BookFormat.HARDCOVER);
        book1.setPageCount(100);
        book1.setLanguageCode("en_GB");
        book1.setQuantity(10);
        book1.setPrice(BigDecimal.valueOf(100.99));
        book1.setPublishedDate(LocalDate.of(2020, 1, 1));
        book1.setGenre(genre1);
        book1.setPublisher(publisher2);
        book1.setAuthors(List.of(author2));

        Book book2 = new Book();
        book2.setTitle("Songs of Ice and Fire, Book 2");
        book2.setIsbn("12345");
        book2.setDescription("A book about the heroes of Ice and Fire");
        book2.setEdition(1);
        book2.setFormat(BookFormat.PAPERBACK);
        book2.setPageCount(150);
        book2.setLanguageCode("en_GB");
        book2.setQuantity(20);
        book2.setPrice(BigDecimal.valueOf(30.99));
        book2.setPublishedDate(LocalDate.of(2020, 1, 1));
        book2.setGenre(genre1);
        book2.setPublisher(publisher2);
        book2.setAuthors(List.of(author2));

        Book book3 = new Book();
        book3.setTitle("Clean Code");
        book3.setIsbn("123456");
        book3.setDescription("A book about clean code");
        book3.setEdition(1);
        book3.setFormat(BookFormat.PAPERBACK);
        book3.setPageCount(300);
        book3.setLanguageCode("en_US");
        book3.setQuantity(20);
        book3.setPrice(BigDecimal.valueOf(15.30));
        book3.setPublishedDate(LocalDate.of(2020, 1, 1));
        book3.setGenre(genre2);
        book3.setPublisher(publisher1);
        book3.setAuthors(List.of(author1));

        Book book4 = new Book();
        book4.setTitle("Na Drini Cuprija");
        book4.setIsbn("1234567");
        book4.setEdition(10);
        book4.setFormat(BookFormat.HARDCOVER);
        book4.setPageCount(100);
        book4.setLanguageCode("sr_RS");
        book4.setQuantity(8);
        book4.setPrice(BigDecimal.valueOf(10.00));
        book4.setPublishedDate(LocalDate.of(2020, 1, 1));
        book4.setGenre(genre3);
        book4.setPublisher(publisher3);
        book4.setAuthors(List.of(author3));

        Book book5 = new Book();
        book5.setTitle("Multiple authors book");
        book5.setIsbn("12345678");
        book5.setFormat(BookFormat.PAPERBACK);
        book5.setPageCount(100);
        book5.setLanguageCode("en_GB");
        book5.setQuantity(18);
        book5.setPrice(BigDecimal.valueOf(10.00));
        book5.setPublishedDate(LocalDate.of(2020, 1, 1));
        book5.setGenre(genre3);
        book5.setPublisher(publisher1);
        book5.setAuthors(List.of(author1, author2, author3));

        return List.of(book1, book2, book3, book4, book5);
    }
}
