package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagDataIncorrectException;
import com.epam.esm.mapper.TagMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class TagVerifier {

    private final TagMapper tagMapper;
    private final MessageSource messageSource;

    public TagVerifier(TagMapper tagMapper, MessageSource messageSource) {
        this.tagMapper = tagMapper;
        this.messageSource = messageSource;
    }

    public void checkAndSaveTagIfNotExist(GiftCertificate giftCertificate, Locale locale) {
        List<Tag> tagList = giftCertificate.getTagList();
        for (Tag tag : tagList) {
            String name = tag.getName();
            if (name == null || name.trim().equals("")) {
                throw new TagDataIncorrectException(messageSource.getMessage("tag.field.incorrect", null, locale));
            }
            Boolean existByName = tagMapper.existByName(tag.getName());
            if (!existByName) {
                tagMapper.save(tag);
            }
        }
    }
}