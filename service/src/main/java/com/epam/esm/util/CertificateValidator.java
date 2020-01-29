package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class CertificateValidator {
    public boolean validate(GiftCertificate giftCertificate){
        return giftCertificate.getName()!=null && giftCertificate.getDescription()!=null
                && giftCertificate.getPrice()!=null && giftCertificate.getDuration()!=0 && giftCertificate.getTagList()!=null;
    }
}
