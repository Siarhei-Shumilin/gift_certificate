package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.TagMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class TagService extends GeneralService {

    private final TagMapper mapperTag;

    public TagService(TagMapper mapperTag) {
        this.mapperTag = mapperTag;
    }

    public long save(Tag tag, Locale locale) {
        if (tag.getName() == null || tag.getName().trim().isEmpty()) {
            throw new GeneralException(ExceptionType.TAG_DATA_INCORRECT, locale);
        }
        mapperTag.save(tag);
        return tag.getId();
    }

    public int delete(String id, Locale locale) {
        long tagId = parseId(id, locale);
        return mapperTag.delete(tagId);
    }

    public List<Tag> findByParameters(Map<String, Object> parameters, Locale locale) {
        String tagName = (String) parameters.get("tagName");
        return mapperTag.findByParameters(tagName, getRowBounds(parameters, locale));
    }

    public Tag findMostPopularTag(){
        return mapperTag.findMostPopularTag();
    }

    public long findIdTag(String tagName){
        return mapperTag.findIdTag(tagName);
    }
}