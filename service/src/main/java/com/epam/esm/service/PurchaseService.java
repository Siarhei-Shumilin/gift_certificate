package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.PurchaseMapper;
import com.epam.esm.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {

    private final UserMapper userMapper;
    private final PurchaseMapper purchaseMapper;
    private final Purchase purchase;

    public PurchaseService(UserMapper userMapper, PurchaseMapper purchaseMapper, Purchase purchase) {
        this.userMapper = userMapper;
        this.purchaseMapper = purchaseMapper;
        this.purchase = purchase;
    }

    public long save(GiftCertificate giftCertificate) {
        User user = getCurrentUser();
        purchase.setUserId(user.getId());
        purchase.setCertificateId(giftCertificate.getId());
        purchase.setPrice(giftCertificate.getPrice());
        purchase.setDateTime(LocalDateTime.now());
        purchaseMapper.save(purchase);
        return purchase.getId();
    }

    public List<Purchase> findUsersPurchases(long userId, Integer page){
        if (page == null) { page = 1; }
        RowBounds rowBounds = new RowBounds(((page - 1) * 5), 5);
        return purchaseMapper.findUsersPurchases(userId, rowBounds);
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