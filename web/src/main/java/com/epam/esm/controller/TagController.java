package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Tag> findByParameters(@RequestParam(required = false) String tagName, Locale locale) {
        return tagService.findByParameters(tagName, locale);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody Tag tag, Locale locale) {
        tagService.save(tag, locale);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        tagService.delete(id);
    }
}