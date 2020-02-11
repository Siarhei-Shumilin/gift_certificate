package com.epam.esm.util;

import java.util.Map;

public class SearchUtil {

    public String findByName(Map<String, Object> parameters) {
        if (parameters.get("tag") != null) {
            return "certificates.name like '" + "%" + parameters.get("name") + "%' and tags.name = \"" + parameters.get("tag") + "\"" + sort(parameters);
        } else {
            return "certificates.name like '" + "%" + parameters.get("name") + "%'";
        }
    }

    public String findDescription(Map<String, Object> parameters) {
        if (parameters.get("tag") != null) {
            return "certificates.description like '" + "%" + parameters.get("description") + "%'" + " and tags.name = \"" + parameters.get("tag") + "\"" + sort(parameters);
        } else {
            return "certificates.description like '" + "%" + parameters.get("description") + "%'" + sort(parameters);
        }
    }

    public String findByTag(Map<String, Object> parameters) {
        return "tags.name = \"" + parameters.get("tag") + "\"" + sort(parameters);
    }

    public String sort(Map<String, Object> parameters) {
        String result = "";
        if (parameters.get("sort") != null) {
            String sort = (String) parameters.get("sort");
            if (sort.equalsIgnoreCase("date")) {
                result = "certificates.last_update_date ";
                if (parameters.get("typeSort") != null) {
                    result = result + parameters.get("typeSort");
                }
            } else if (sort.equalsIgnoreCase("name")) {
                result = "certificates.name ";
                if (parameters.get("typeSort") != null) {
                    result = result + parameters.get("typeSort");
                }
            }
        }
        return result;
    }
}