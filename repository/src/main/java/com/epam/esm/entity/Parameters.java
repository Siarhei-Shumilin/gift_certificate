package com.epam.esm.entity;

import java.io.Serializable;

public class Parameters implements Serializable {
    private String name;
    private String description;
    private String tagName;
    private String sort;
    private String typeSort;
    private int page;

    public Parameters() {
    }

    public Parameters(String name, String description, String tagName, String sort, String typeSort, int page) {
        this.name = name;
        this.description = description;
        this.tagName = tagName;
        this.sort = sort;
        this.typeSort = typeSort;
        this.page = page;
    }

    public Parameters(String name, String description, String tagName, String sort, String typeSort) {
        this.name = name;
        this.description = description;
        this.tagName = tagName;
        this.sort = sort;
        this.typeSort = typeSort;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(String typeSort) {
        this.typeSort = typeSort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}