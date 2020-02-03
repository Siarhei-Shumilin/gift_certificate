package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public String buy(@RequestBody GiftCertificate giftCertificate) {
        return purchaseService.buy(giftCertificate);
    }

    @GetMapping("/{userId}")
    public List<Purchase> findUsersPurchases(@PathVariable long userId, @RequestParam(required = false) Integer page){
        return purchaseService.findUsersPurchases(userId, page);
    }
}
