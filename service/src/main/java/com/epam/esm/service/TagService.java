package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagDataIncorrectException;
import com.epam.esm.mapper.TagMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;

@Service
public class TagService {

    private final TagMapper mapperTag;
    private final MessageSource messageSource;

    public TagService(TagMapper mapperTag, MessageSource messageSource) {
        this.mapperTag = mapperTag;
        this.messageSource = messageSource;
    }

    public long save(Tag tag, Locale locale) {
        if (tag.getName() == null || tag.getName().trim().equals("")) {
            throw new TagDataIncorrectException(messageSource.getMessage("tag.field.incorrect", null, locale));
        }
        mapperTag.save(tag);
        return tag.getId();
    }

    public int delete(int id) {
        return mapperTag.delete(id);
    }

    public List<Tag> findByParameters(String tagName) {
        return mapperTag.findByParameters(tagName);
    }

    public Tag findMostPopularTag(){
        return mapperTag.findMostPopularTag();
    }
}