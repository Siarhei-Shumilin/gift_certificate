package com.epam.esm.service;

import com.epam.esm.mapper.PurchaseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {
    @Mock
    private PurchaseMapper purchaseMapper;
    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    public void buy() {

    }

    @Test
    public void findUsersPurchases() {

    }
}