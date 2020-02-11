package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.CertificateDataIncorrectException;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class CertificateServiceTest {
    @Mock
    private CertificateMapper mapper;
    @Mock
    private CertificateValidator validator;
    @Mock
    private TagVerifier tagVerifier;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private CertificateService service;

    @Test
    public void testDeleteShouldCallMappersMethodDelete() {
        service.delete(1);
        Mockito.verify(mapper, Mockito.times(1)).delete(1);
    }

    @Test(expected = CertificateNotFoundException.class)
    public void testFindByParameters(){
        service.findByParameters(new HashMap<>(), new ArrayList<>(), new Locale("en"));
    }

    @Test(expected = CertificateDataIncorrectException.class)
    public void testSave(){
        service.save(new GiftCertificate(), new Locale("en"));
    }

    @Test(expected = CertificateDataIncorrectException.class)
    public void testUpdate(){
        service.update(new GiftCertificate(), new Locale("en"));
    }
}