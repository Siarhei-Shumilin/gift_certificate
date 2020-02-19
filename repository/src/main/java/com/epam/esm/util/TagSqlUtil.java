package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class TagSqlUtil {
    public String getTagByParameter(String name) {
        SQL sql = new SQL();
        sql.SELECT("id, name");
        sql.FROM("tags");
        if (name != null && !name.trim().isEmpty()) {
        sql.WHERE("name like #{name}");
        }
        sql.ORDER_BY("id");
        return sql.toString();
    }
}