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

@RunWith(MockitoJUnitRunner.class)
public class TagValidatorTest {
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private TagValidator tagValidator;

    @Test(expected = GeneralException.class)
    public void testCheckAndSaveTagIfNotExistShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setTagList(Arrays.asList(new Tag()));
        tagValidator.checkAndSaveTagIfNotExist(giftCertificate);
    }

    @Test
    public void testCheckAndSaveTag() {
        GiftCertificate giftCertificate = new GiftCertificate();
        Tag tag = new Tag();
        tag.setName("name");
        giftCertificate.setTagList(Arrays.asList(tag));
        tagValidator.checkAndSaveTagIfNotExist(giftCertificate);
        Mockito.verify(tagMapper, Mockito.times(1)).saveListTags(giftCertificate.getTagList());
    }
}