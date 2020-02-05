package com.epam.esm.entity;

import java.io.Serializable;
import java.util.List;

public class Parameters implements Serializable {
    private String name;
    private String description;
    private String tagName;
    private String sort;
    private String typeSort;
    private Integer page;
    private List<String> listTagName;

    public Parameters() {
    }

    public Parameters(String name, String description, List<String> listTagName, String sort, String typeSort, Integer page) {
        this.name = name;
        this.description = description;
        this.listTagName = listTagName;
        this.sort = sort;
        this.typeSort = typeSort;
        this.page = page;
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

    public List<String> getListTagName() {
        return listTagName;
    }

    public void setListTagName(List<String> listTagName) {
        this.listTagName = listTagName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(String typeSort) {
        this.typeSort = typeSort;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}