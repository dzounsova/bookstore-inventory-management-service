package com.eli.bims.integration;

import com.eli.bims.config.TestBase;
import com.eli.bims.dao.Book;
import com.eli.bims.dto.request.PurchaseRequest;
import com.eli.bims.repository.BookRepository;
import com.eli.bims.service.PurchaseService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseServiceIntegrationTest extends TestBase {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    void setUpAll() {
        bookRepository.saveAll(getMockBooks());
    }

    @AfterAll
    void tearDownAll() {
        bookRepository.deleteAll();
        bookRepository.flush();
    }

    @Test
    void buy_shouldDecrementQuantityOfAllPurchasedBooks() {
        PurchaseRequest request1 = new PurchaseRequest();
        request1.setBookId(1L);
        request1.setQuantity(1);
        PurchaseRequest request2 = new PurchaseRequest();
        request2.setBookId(2L);
        request2.setQuantity(3);

        purchaseService.buy(List.of(request1, request2));

        List<Book> books = bookRepository.findAllById(List.of(1L, 2L));
        assertEquals(2, books.size());
        assertThat(books).extracting(Book::getId, Book::getQuantity)
                .containsExactlyInAnyOrder(tuple(1L, 9), tuple(2L, 17));
    }

    @Test
    void cancel_shouldIncrementQuantityOfAllReturnedBooks() {
        PurchaseRequest request1 = new PurchaseRequest();
        request1.setBookId(1L);
        request1.setQuantity(1);
        PurchaseRequest request2 = new PurchaseRequest();
        request2.setBookId(2L);
        request2.setQuantity(3);

        purchaseService.cancel(List.of(request1, request2));

        List<Book> books = bookRepository.findAllById(List.of(1L, 2L));
        assertEquals(2, books.size());
        assertThat(books).extracting(Book::getId, Book::getQuantity)
                .containsExactlyInAnyOrder(tuple(1L, 10), tuple(2L, 20));
    }
}
