package com.eli.bims.service;

import com.eli.bims.dto.request.PurchaseRequest;

import java.util.List;

public interface PurchaseService {

    void buy(List<PurchaseRequest> purchaseRequests);

    void cancel(List<PurchaseRequest> purchaseRequests);
}
