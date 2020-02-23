package com.epam.esm.service;

import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.PurchaseMapper;
import com.epam.esm.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    @Mock
    CertificateMapper certificateMapper;
    @Mock
    UserMapper userMapper;
    @Mock
    private PurchaseMapper purchaseMapper;
    private PurchaseService purchaseService = new PurchaseService(userMapper, certificateMapper, purchaseMapper);

    @Test(expected = GeneralException.class)
    public void testFindUsersPurchasesShouldThrowException() {
        String userId = "lm1";
        Map<String, Object> parameters = new HashMap<>();
        Locale locale = new Locale("en");
        purchaseService.findUsersPurchases(userId, parameters, locale);
    }
}