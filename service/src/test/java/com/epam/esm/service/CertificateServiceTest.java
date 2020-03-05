package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagValidator;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class CertificateServiceTest {
    @Mock
    private CertificateMapper mapper;
    @Mock
    private CertificateValidator validator;
    @Mock
    private TagValidator tagValidator;
    @Mock
    private TagService tagService;
    @Mock
    private TagMapper tagMapper;
    @Spy
    @InjectMocks
    private CertificateService service;

    @Test
    public void testDeleteShouldCallMappersMethodDelete() {
        service.delete("1");
        Mockito.verify(mapper, Mockito.times(1)).delete(1);
    }

    @Test(expected = GeneralException.class)
    public void testSaveShouldThrowException(){
        service.save(new GiftCertificate());
    }

    @Test(expected = GeneralException.class)
    public void testUpdateShouldThrowException(){
        service.update("1", new GiftCertificate());
    }

    @Test(expected = GeneralException.class)
    public void testUpdatePriceShouldThrowException(){
        service.updatePrice("1", new GiftCertificate());
    }

    @Test
    public void testUpdatePriceShould(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(new BigDecimal(2));
        service.updatePrice("1", giftCertificate);
        Mockito.verify(mapper, Mockito.times(1)).updatePrice(giftCertificate);
    }

    @Test
    public void testFindByParametersShouldMapperCallFindByParametersMethod() {
        Map<String, Object> parameters = new HashMap<>();
        List<String> tagList = new ArrayList<>();
        RowBounds rowBounds = Mockito.mock(RowBounds.class);
        Mockito.when(service.getRowBounds(parameters)).thenReturn(rowBounds);
        service.findByParameters(parameters, tagList);
        Mockito.verify(mapper, Mockito.times(1)).findByParameters(parameters, tagList, rowBounds);
    }

    @Test(expected = GeneralException.class)
    public void testFindByIdShouldThrowException() {
        service.findById("1fg");
    }
}