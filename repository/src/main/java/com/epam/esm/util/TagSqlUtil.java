package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class TagSqlUtil {
    public String getTagByParameter(String name) {
              return   new SQL() {{
            SELECT("id, name");
            FROM("tags");
            if (name != null) {
                WHERE("name like #{name}");
            }
        }}.toString();
    }
}