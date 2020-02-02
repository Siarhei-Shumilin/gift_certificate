package com.epam.esm.util;

import com.epam.esm.entity.Parameters;

public class SearchUtil {

    public String findByName(Parameters parameters){
        if (parameters.getTagName() != null){
            return  "certificates.name like '" + "%" + parameters.getName() + "%' and tags.name = \"" + parameters.getTagName() + "\"" + sort(parameters);
        } else {
           return  "certificates.name like '" + "%" + parameters.getName() + "%'" + sort(parameters);
        }
    }

    public String findDescription(Parameters parameters){
        if (parameters.getTagName() != null){
            return "certificates.description like '" + "%" + parameters.getDescription() + "%'" + " and tags.name = \"" + parameters.getTagName() + "\"" + sort(parameters);
        } else {
           return "certificates.description like '" + "%" + parameters.getDescription() + "%'" + sort(parameters);
        }
    }

    public String findByTag(Parameters parameters) {
       return  "tags.name = \"" + parameters.getTagName() + "\"" + sort(parameters);
    }

    public String sort(Parameters parameters){
        String result = "";
        if (parameters.getSort() != null) {
            if (parameters.getSort().equalsIgnoreCase("date")) {
                if (parameters.getTypeSort() != null) {
                    result = "certificates.last_update_date " + parameters.getTypeSort();
                } else {
                    result = "certificates.last_update_date";
                }
            } else if (parameters.getSort().equalsIgnoreCase("name")) {
                if (parameters.getTypeSort() != null) {
                    result = "certificates.name " + parameters.getTypeSort();
                } else {
                    result = "certificates.name";
                }
            }
        }
        return result;
    }
}