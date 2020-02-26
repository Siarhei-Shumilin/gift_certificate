package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import java.util.List;
import java.util.Map;

public class SearchUtil {
     private static final String DESCRIPTION = "description";

    public String findByName(Map<String, Object> parameters, List<String> tagList, SQL sql) {
        String query = "certificates.name like '" + "%" + parameters.get("name") + "%' ";
        if (parameters.get(DESCRIPTION) != null) {
            query = query + " and certificates.description like '" + "%" + parameters.get(DESCRIPTION) + "%'";
        }
        if (tagList != null) {
            query = query + " and " + findByTag(tagList, sql);
        }
        return query;
    }

    public String findDescription(Map<String, Object> parameters, List<String> tagList, SQL sql) {
        String query = "certificates.description like '" + "%" + parameters.get(DESCRIPTION) + "%'";
        if (tagList != null) {
            query = query + " and " + findByTag(tagList, sql);
        }
        return query;
    }

    public String findByTag(List<String> tagList, SQL sql) {
        StringBuilder query = new StringBuilder("tags.name IN (");
        if (tagList.size() == 1) {
            query.append("\"").append(tagList.get(0)).append("\")");
        } else {
            for (String tagName : tagList) {
                query.append("\"").append(tagName).append("\",");
            }
            String string = query.toString().replaceAll(",$", "");
            query = new StringBuilder(string + ")");
            sql.GROUP_BY("certificates.id having count(tags.id)>=" + tagList.size());
        }
        return query.toString();
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