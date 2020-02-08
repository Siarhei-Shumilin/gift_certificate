package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CertificateValidatorTest {

    private CertificateValidator validator = new CertificateValidator();
    @Test
    public void validate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("name");
        giftCertificate.setPrice(new BigDecimal(10));
        giftCertificate.setDescription("description");
        giftCertificate.setDuration(1);
        boolean validate = validator.validate(giftCertificate);
        Assert.assertFalse(validate);
    }
}