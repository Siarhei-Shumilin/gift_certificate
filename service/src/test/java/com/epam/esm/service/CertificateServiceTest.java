package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
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
    private TagVerifier tagVerifier;
    @Mock
    private TagService tagService;
    @Mock
    private SqlSessionFactory sqlSessionFactory;
    @Spy
    @InjectMocks
    private CertificateService service;

    @Test
    public void testDeleteShouldCallMappersMethodDelete() {
        Locale locale = new Locale("en");
        service.delete("1", locale);
        Mockito.verify(mapper, Mockito.times(1)).delete(1);
    }

    @Test(expected = GeneralException.class)
    public void testSaveShouldThrowException(){
        service.save(new GiftCertificate(), new Locale("en"));
    }

    @Test(expected = GeneralException.class)
    public void testUpdateShouldThrowException(){
        service.update("1", new GiftCertificate(), new Locale("en"));
    }

    @Test(expected = GeneralException.class)
    public void testUpdatePriceShouldThrowException(){
        service.updatePrice("1", new GiftCertificate(), new Locale("en"));
    }

    @Test
    public void testUpdatePriceShould(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(new BigDecimal(2));
        service.updatePrice("1", giftCertificate, new Locale("en"));
        Mockito.verify(mapper, Mockito.times(1)).updatePrice(giftCertificate);
    }

    @Test
    public void testFindByParametersShouldMapperCallFindByParametersMethod() {
        Map<String, Object> parameters = new HashMap<>();
        List<String> tagList = new ArrayList<>();
        Locale locale = new Locale("en");
        RowBounds rowBounds = Mockito.mock(RowBounds.class);
        Mockito.when(service.getRowBounds(parameters, locale)).thenReturn(rowBounds);
        service.findByParameters(parameters, tagList, locale);
        Mockito.verify(mapper, Mockito.times(1)).findByParameters(parameters, tagList, rowBounds);
    }
}