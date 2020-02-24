package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.TagMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class TagVerifierTest {
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private TagVerifier tagVerifier;

    @Test(expected = GeneralException.class)
    public void testCheckAndSaveTagIfNotExistShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setTagList(Arrays.asList(new Tag()));
        tagVerifier.checkAndSaveTagIfNotExist(giftCertificate, new Locale("en"));
    }

    @Test
    public void testCheckAndSaveTag() {
        GiftCertificate giftCertificate = new GiftCertificate();
        Tag tag = new Tag();
        tag.setName("name");
        giftCertificate.setTagList(Arrays.asList(tag));
        tagVerifier.checkAndSaveTagIfNotExist(giftCertificate, new Locale("en"));
        Mockito.verify(tagMapper, Mockito.times(1)).saveListTags(giftCertificate.getTagList());
    }
}