package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService {

    private final TagMapper mapperTag;

    public TagService(TagMapper mapperTag) {
        this.mapperTag = mapperTag;
    }

    public void save(Tag tag) {
        if (tag.getName() == null) {
            throw new TagNotFoundException("The tag cannot be null");
        }
        mapperTag.save(tag);
    }

    public boolean delete(int id) {
        return mapperTag.delete(id);
    }

    public List<Tag> findByParameters(String tagName){
        return mapperTag.findByParameters(tagName);
    }
}