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

    /**
     * @return {@code true} if this GiftCertificate contains valid fields.
     */
    public boolean validate(GiftCertificate giftCertificate) {
        boolean name = validateName(giftCertificate);
        boolean description = validateDescription(giftCertificate);
        boolean price = validatePrice(giftCertificate);
        boolean duration = isMoreZero.test(giftCertificate.getDuration());
        boolean tagList = isNotNull.test(giftCertificate.getTagList());

        return name && description && price && duration && tagList;
    }

    /**
     * @return {@code true} if this GiftCertificate contains valid name.
     */
    private boolean validateName(GiftCertificate giftCertificate){
        boolean name = isNotNull.test(giftCertificate.getName());
        boolean emptyName = false;
        if (name){
            emptyName = giftCertificate.getName().trim().isEmpty();
        }
        return emptyName;
    }

    /**
     * @return {@code true} if this GiftCertificate contains valid description.
     */
    private boolean validateDescription(GiftCertificate giftCertificate){
        boolean description = isNotNull.test(giftCertificate.getDescription());
        boolean emptyDescription = false;
        if (description){
            emptyDescription = giftCertificate.getDescription().trim().isEmpty();
        }
        return emptyDescription;
    }

    /**
     * @return {@code true} if this GiftCertificate contains valid price.
     */
    public boolean validatePrice(GiftCertificate giftCertificate){
        boolean price = isNotNull.test(giftCertificate.getPrice());
        boolean positivePrice = false;
        if(price){
            positivePrice = isMoreZero.test(giftCertificate.getPrice().intValue());
        }
        return positivePrice;
    }
}