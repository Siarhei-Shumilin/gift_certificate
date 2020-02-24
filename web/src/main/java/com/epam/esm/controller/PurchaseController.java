package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public Map<String, String> save(@RequestBody GiftCertificate giftCertificate, Locale locale) {
        Map<String, String> map = new HashMap<>();
        map.put("Message" , "Purchase id = " + purchaseService.save(giftCertificate, locale));
        return map;
    }

    @GetMapping("/{userId}")
    public Map<String, Object> findUsersPurchases(@PathVariable String userId, @RequestParam(required = false) Map<String, Object> parameters, Locale locale){
        Map<String, Object> map = new HashMap<>();
        List<Purchase> usersPurchases = purchaseService.findUsersPurchases(userId, parameters, locale);
        for (Purchase purchase : usersPurchases) {
            map.put("Purchase id = " + purchase.getId(),"date = " + purchase.getDateTime() + ", " + "price = " + purchase.getPrice());
        }
        return map;
    }

    @GetMapping
    public List<Purchase> findCurrentUserPurchase(@RequestParam(required = false) Map<String, Object> parameters, Locale locale){
        return purchaseService.findCurrentUserPurchases(parameters, locale);
    }
}