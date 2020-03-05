package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.TagMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagValidator {

    private final TagMapper tagMapper;

    public TagValidator(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public void checkAndSaveTagIfNotExist(GiftCertificate giftCertificate) {
        List<Tag> tagList = giftCertificate.getTagList();
        for (Tag tag : tagList) {
            String name = tag.getName();
            if (name == null || name.trim().isEmpty()) {
                throw new GeneralException(ExceptionType.TAG_DATA_INCORRECT);
            }
        }
        tagMapper.saveListTags(tagList);
    }
}