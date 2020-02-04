package com.epam.esm.service;

import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


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
        service.delete(1);
        Mockito.verify(mapper, Mockito.times(1)).delete(1);
    }
}