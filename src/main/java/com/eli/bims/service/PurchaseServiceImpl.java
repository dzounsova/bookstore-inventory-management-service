package com.eli.bims.service;

import com.eli.bims.dto.request.PurchaseRequest;
import com.eli.bims.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final BookRepository bookRepository;

    public PurchaseServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public void buy(final List<PurchaseRequest> purchaseRequests) {
        try {
            for (PurchaseRequest purchaseRequest : purchaseRequests) {
                bookRepository.buy(purchaseRequest.getBookId(), purchaseRequest.getQuantity());
            }
            log.info("Purchased books: {}", purchaseRequests);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Not enough books in stock", ex);
        }
    }

    @Transactional
    @Override
    public void cancel(final List<PurchaseRequest> purchaseRequests) {
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            bookRepository.cancel(purchaseRequest.getBookId(), purchaseRequest.getQuantity());
        }
        log.info("Cancelled purchased books: {}", purchaseRequests);
    }
}
