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

    /**
     * Get the ID of the user who makes the purchase. Check whether the certificate exists in the database.
     * If all conditions are met, we save the purchase.
     *
     * @return {@code true} the purchase ID.
     * @throws GeneralException if the certificate for purchase does not exist.
     */
    public long save(GiftCertificate giftCertificate) {
        User user = getCurrentUser();
        Purchase purchase;
        if (certificateMapper.existById(giftCertificate.getId()) && user != null) {
            purchase = new Purchase();
            purchase.setUserId(user.getId());
            purchase.setCertificateId(giftCertificate.getId());
            purchase.setDateTime(LocalDateTime.now());
            purchaseMapper.save(purchase);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_NOT_EXISTS);
        }
        return purchase.getId();
    }

    /**
     * Find user purchases by Id.
     *
     * @return the user list purchases.
     * @throws GeneralException if the user ID format is incorrect.
     */
    public List<Purchase> findUsersPurchases(String userId, Map<String, Object> parameters) {
        long id = parseId(userId);
        return purchaseMapper.findUsersPurchases(id, getRowBounds(parameters));
    }

    /**
     * Get the ID of the user who logged in.
     * Find current user purchases if the user is not null.
     *
     * @return the user list purchases.
     */
    public List<Purchase> findCurrentUserPurchases(Map<String, Object> parameters) {
        User user = getCurrentUser();
        List<Purchase> usersPurchases = new ArrayList<>();
        if (user != null) {
            long id = user.getId();
            usersPurchases = purchaseMapper.findUsersPurchases(id, getRowBounds(parameters));
        }
        return usersPurchases;
    }

    /**
     * @return the user who logged in.
     */
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