package com.eli.bims.controller;

import com.eli.bims.dto.request.PurchaseRequest;
import com.eli.bims.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(final PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/buy")
    public void buy(@Valid @RequestBody final List<PurchaseRequest> requests) {
        log.info("Received purchase request");
        purchaseService.buy(requests);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cancel")
    public void cancel(@Valid @RequestBody final List<PurchaseRequest> requests) {
        log.info("Received cancel purchase request");
        purchaseService.cancel(requests);
    }
}
