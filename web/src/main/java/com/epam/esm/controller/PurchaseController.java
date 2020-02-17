package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public long save(@RequestBody GiftCertificate giftCertificate, Locale locale) {
        return purchaseService.save(giftCertificate, locale);
    }

    @GetMapping("/{userId}")
    public List<Purchase> findUsersPurchases(@PathVariable long userId, @RequestParam(required = false) String page){
        return purchaseService.findUsersPurchases(userId, page);
    }

    @GetMapping
    public List<Purchase> findCurrentUserPurchase(@RequestParam(required = false) String page){
        return purchaseService.findCurrentUserPurchases(page);
    }
}