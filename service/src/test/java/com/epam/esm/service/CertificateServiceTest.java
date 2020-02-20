package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class CertificateServiceTest {
    @Mock
    private CertificateMapper mapper;
    @Mock
    private CertificateValidator validator;
    @Mock
    private TagVerifier tagVerifier;
    @InjectMocks
    private CertificateService service;

    @Test
    public void testDeleteShouldCallMappersMethodDelete() {
        Locale locale = new Locale("en");
        service.delete("1", locale);
        Mockito.verify(mapper, Mockito.times(1)).delete(1);
    }

    @Test(expected = GeneralException.class)
    public void testSave(){
        service.save(new GiftCertificate(), new Locale("en"));
    }

    @Test(expected = GeneralException.class)
    public void testUpdate(){
        service.update("1", new GiftCertificate(), new Locale("en"));
    }
}