package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class TagService {

    private final TagMapper mapperTag;

    public TagService(TagMapper mapperTag) {
        this.mapperTag = mapperTag;
    }

    public long save(Tag tag, Locale locale) {
        if (tag.getName() == null || tag.getName().trim().equals("")) {
            throw new GeneralException(ExceptionType.TAG_DATA_INCORRECT, locale);
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