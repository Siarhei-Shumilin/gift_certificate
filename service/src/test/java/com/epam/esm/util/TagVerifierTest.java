package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagDataIncorrectException;
import com.epam.esm.mapper.TagMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class TagVerifierTest {
    @Mock
    private TagMapper tagMapper;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private TagVerifier tagVerifier;

    @Test(expected = TagDataIncorrectException.class)
    public void testCheckAndSaveTagIfNotExistShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setTagList(Arrays.asList(new Tag()));
        tagVerifier.checkAndSaveTagIfNotExist(giftCertificate, new Locale("en"));
    }
}