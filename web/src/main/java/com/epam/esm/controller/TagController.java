package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping(value = "/tags",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
        consumes = {MediaType.APPLICATION_JSON_VALUE})
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> findByParameters(@RequestParam(required = false) Map<String, Object> parameters, Locale locale) {
        return tagService.findByParameters(parameters, locale);
    }

    @PostMapping
    public long save(@RequestBody Tag tag, Locale locale) {
        return tagService.save(tag, locale);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable String id, Locale locale) {
        return tagService.delete(id, locale);
    }

    @GetMapping("/popular")
    public Tag findMostPopularTag(){
        return tagService.findMostPopularTag();
    }
}