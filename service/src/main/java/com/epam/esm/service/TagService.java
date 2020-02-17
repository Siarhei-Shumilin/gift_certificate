package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.TagMapper;
import org.apache.ibatis.session.RowBounds;
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

    public List<Tag> findByParameters(String tagName, String page) {
        return mapperTag.findByParameters(tagName, getRowBounds(page));
    }

    public Tag findMostPopularTag(){
        return mapperTag.findMostPopularTag();
    }

    public long findIdTag(String tagName){
        return mapperTag.findIdTag(tagName);
    }

    private RowBounds getRowBounds(String page) {
        int currentPage;
        if (page == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(page);
        }
        return new RowBounds(((currentPage-1) * 5), 5);
    }
}