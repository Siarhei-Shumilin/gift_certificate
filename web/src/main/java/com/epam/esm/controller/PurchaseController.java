package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public Map<String, Long> save(@RequestBody GiftCertificate giftCertificate) {
        return Map.of("Purchase id " , purchaseService.save(giftCertificate));
    }

    @GetMapping("/user/{userId}")
    public List<Purchase> findUsersPurchases(@PathVariable String userId, @RequestParam(required = false) Map<String, Object> parameters){
        return purchaseService.findUsersPurchases(userId, parameters);
    }

    @GetMapping
    public List<Purchase> findCurrentUserPurchase(@RequestParam(required = false) Map<String, Object> parameters){
        return purchaseService.findCurrentUserPurchases(parameters);
    }
}