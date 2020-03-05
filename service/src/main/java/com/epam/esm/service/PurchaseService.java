package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.PurchaseMapper;
import com.epam.esm.mapper.UserMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService extends GeneralService {

    private final UserMapper userMapper;
    private final CertificateMapper certificateMapper;
    private final PurchaseMapper purchaseMapper;

    public PurchaseService(UserMapper userMapper, CertificateMapper certificateMapper, PurchaseMapper purchaseMapper) {
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
        this.purchaseMapper = purchaseMapper;
    }

    public long save(GiftCertificate giftCertificate) {
        User user = getCurrentUser();
        long purchaseId;
        if (certificateMapper.existById(giftCertificate.getId()) && user != null) {
            Purchase purchase = new Purchase();
            purchase.setUserId(user.getId());
            purchase.setCertificateId(giftCertificate.getId());
            purchase.setDateTime(LocalDateTime.now());
            purchaseMapper.save(purchase);
            purchaseId = purchase.getId();
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_NOT_EXISTS);
        }
        return purchaseId;
    }

    public List<Purchase> findUsersPurchases(String userId, Map<String, Object> parameters) {
        long id = parseId(userId);
        return purchaseMapper.findUsersPurchases(id, getRowBounds(parameters));
    }

    public List<Purchase> findCurrentUserPurchases(Map<String, Object> parameters) {
        User user = getCurrentUser();
        List<Purchase> usersPurchases = new ArrayList<>();
        if (user != null) {
            long id = user.getId();
            usersPurchases = purchaseMapper.findUsersPurchases(id, getRowBounds(parameters));
        }
        return usersPurchases;
    }

    public User getCurrentUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            user = userMapper.findByUserName(currentUserName);
        }
        return user;
    }
}