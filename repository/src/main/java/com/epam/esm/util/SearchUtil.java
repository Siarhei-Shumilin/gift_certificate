package com.epam.esm.util;

import java.util.Map;

public class SearchUtil {

    public String findByName(Map<String, Object> parameters) {
        String query = "certificates.name like '" + "%" + parameters.get("name") + "%' ";
        if(parameters.get("description")!=null){
            query = query + " and certificates.description like '" + "%" + parameters.get("description") + "%'";
        }
        if (parameters.get("tag") != null) {
            query = query + " and tags.name = \"" + parameters.get("tag") + "\"";
        }
        return query;
    }

    public String findDescription(Map<String, Object> parameters) {
        if (parameters.get("tag") != null) {
            return "certificates.description like '" + "%" + parameters.get("description") + "%'" + " and tags.name = \"" + parameters.get("tag") + "\"";
        } else {
            return "certificates.description like '" + "%" + parameters.get("description") + "%'";
        }
    }

    public String findByTag(Map<String, Object> parameters) {
        return "tags.name = \"" + parameters.get("tag") + "\"";
    }

    public String sort(Map<String, Object> parameters) {
        String result = "";
        if (parameters.get("sort") != null) {
            String sort = (String) parameters.get("sort");
            if (sort.equalsIgnoreCase("date")) {
                result = "certificates.last_update_date ";
            } else if (sort.equalsIgnoreCase("name")) {
                result = "certificates.name ";
            }
            String typeSort = (String) parameters.get("typeSort");
            if (typeSort != null && typeSort.equalsIgnoreCase("DESC")) {
                result = result + typeSort;
            }
        }
        return result;
    }
}