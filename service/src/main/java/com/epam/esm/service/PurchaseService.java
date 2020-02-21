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
import java.util.Locale;
import java.util.Map;

@Service
public class PurchaseService extends GeneralService {

    private final UserMapper userMapper;
    private final CertificateMapper certificateMapper;
    private final PurchaseMapper purchaseMapper;
    private final Purchase purchase;

    public PurchaseService(UserMapper userMapper, CertificateMapper certificateMapper, PurchaseMapper purchaseMapper, Purchase purchase) {
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
        this.purchaseMapper = purchaseMapper;
        this.purchase = purchase;
    }

    public long save(GiftCertificate giftCertificate, Locale locale) {
        if (certificateMapper.existById(giftCertificate.getId())) {
            User user = getCurrentUser();
            if(user!=null) {
                purchase.setUserId(user.getId());
                purchase.setCertificateId(giftCertificate.getId());
                purchase.setDateTime(LocalDateTime.now());
                purchaseMapper.save(purchase);
            }
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_NOT_EXISTS, locale);
        }
        return purchase.getId();
    }

    public List<Purchase> findUsersPurchases(String userId, Map<String, Object> parameters, Locale locale) {
        long id;
            try {
                id = Long.parseLong(userId);
            } catch (NumberFormatException e) {
                throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT, locale);
            }

        return purchaseMapper.findUsersPurchases(id, getRowBounds(parameters, locale));
    }

    public List<Purchase> findCurrentUserPurchases(Map<String, Object> parameters, Locale locale) {
        User user = getCurrentUser();
        List<Purchase> usersPurchases = new ArrayList<>();
        if (user!=null){
            long id = user.getId();
            usersPurchases = purchaseMapper.findUsersPurchases(id, getRowBounds(parameters, locale));
        }
        return usersPurchases;
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