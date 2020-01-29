package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagMapperMyBatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagVerifier {

    @Autowired
    private TagMapperMyBatis tagMapper;

    public void checkAndSaveTagIfNotExist(GiftCertificate giftCertificate){
            List<Tag> tagList = giftCertificate.getTagList();
            for (Tag tag : tagList) {
                String name = tag.getName();
                if (name == null) {
                    throw new TagNotFoundException("The tag name cannot be null");
                }
                Boolean existByName = tagMapper.existByName(tag.getName());
                if (!existByName) {
                    tagMapper.save(tag);
                }
            }
    }
}