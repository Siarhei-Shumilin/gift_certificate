package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class CertificateValidator {
    public boolean validate(GiftCertificate giftCertificate) {
        return giftCertificate.getName() != null && !giftCertificate.getName().trim().equals("")
                && giftCertificate.getDescription() != null && !giftCertificate.getDescription().trim().equals("")
                && giftCertificate.getPrice() != null && giftCertificate.getPrice().intValue() > 0
                && giftCertificate.getDuration() >= 0 && giftCertificate.getTagList() != null;
    }
}