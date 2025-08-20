package com.eli.bims.service;

import com.eli.bims.dto.request.PurchaseRequest;
import com.eli.bims.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    PurchaseServiceImpl purchaseService;

    @Test
    void buy_shouldBuyAllBooks() {
        PurchaseRequest request1 = new PurchaseRequest();
        request1.setBookId(100L);
        request1.setQuantity(10);
        PurchaseRequest request2 = new PurchaseRequest();
        request2.setBookId(200L);
        request2.setQuantity(2);

        purchaseService.buy(List.of(request1, request2));

        verify(bookRepository, times(2)).buy(any(Long.class), any(Integer.class));
    }

    @Test
    void buy_shouldCancelAllBooks() {
        PurchaseRequest request1 = new PurchaseRequest();
        request1.setBookId(100L);
        request1.setQuantity(10);
        PurchaseRequest request2 = new PurchaseRequest();
        request2.setBookId(200L);
        request2.setQuantity(2);

        purchaseService.cancel(List.of(request1, request2));

        verify(bookRepository, times(2)).cancel(any(Long.class), any(Integer.class));
    }
}