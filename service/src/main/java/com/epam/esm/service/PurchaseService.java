package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.PurchaseMapper;
import com.epam.esm.mapper.UserMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class PurchaseService {

    private final UserMapper userMapper;
    private final PurchaseMapper purchaseMapper;

    public PurchaseService(UserMapper userMapper, PurchaseMapper purchaseMapper) {
        this.userMapper = userMapper;
        this.purchaseMapper = purchaseMapper;
    }

    public String buy(GiftCertificate giftCertificate) {
        User user = getCurrentUser();
        long userId = user.getId();
        Timestamp date = Timestamp.from(Instant.now());
        Purchase purchase = new Purchase(userId, giftCertificate.getId(), giftCertificate.getPrice(), date.toLocalDateTime());
        purchaseMapper.buy(purchase);
        return "Purchase number = " + purchase.getId();
    }

    private User getCurrentUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            user = userMapper.findByUserName(currentUserName);
        }
        return user;
    }
}