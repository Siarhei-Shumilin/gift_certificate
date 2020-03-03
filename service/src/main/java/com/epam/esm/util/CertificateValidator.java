package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

@Component
public class CertificateValidator {

    private Predicate<Object> isNotNull = Objects::nonNull;
    private IntPredicate isMoreZero = x -> x > 0;

    public boolean validate(GiftCertificate giftCertificate) {
        boolean name = isNotNull.test(giftCertificate.getName());
        boolean description = isNotNull.test(giftCertificate.getDescription());
        boolean price = isNotNull.test(giftCertificate.getPrice());
        boolean tagList = isNotNull.test(giftCertificate.getTagList());
        boolean positivePrice = false;
        if(price){
            positivePrice = isMoreZero.test(giftCertificate.getPrice().intValue());
        }
        boolean duration = isMoreZero.test(giftCertificate.getDuration());
        boolean emptyName = false;
        if (name){
            emptyName = giftCertificate.getName().trim().isEmpty();
        }
        boolean emptyDescription = false;
        if (description){
            emptyDescription = giftCertificate.getDescription().trim().isEmpty();
        }
        return name && !emptyName && description && !emptyDescription && price && positivePrice && duration && tagList;
    }
}