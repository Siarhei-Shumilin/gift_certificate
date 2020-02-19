package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CertificateSqlUtil {
    private final String selectData = "certificates.id, certificates.name, certificates.description, "
            + "certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration";
    private final String table = "certificates";
    private final String innerJoinCertificate = "connecting ON connecting.certificate_id=certificates.id";
    private final String innerJoinTag = "tags ON connecting.tag_id=tags.id";

    public String getByParameter(Map<String, Object> parameters, List<String> tagList) {
        SearchUtil searchUtil = new SearchUtil();
        SQL sql = new SQL();
        sql.SELECT_DISTINCT(selectData);
        sql.FROM(table);
        sql.INNER_JOIN(innerJoinCertificate);
        sql.INNER_JOIN(innerJoinTag);
        if (parameters.get("name") != null) {
            sql.WHERE(searchUtil.findByName(parameters, tagList, sql));
        } else if (parameters.get("description") != null) {
            sql.WHERE(searchUtil.findDescription(parameters, tagList, sql));
        } else if (tagList != null) {
            sql.WHERE(searchUtil.findByTag(tagList, sql));
        }
        String sort = sort(parameters);
        sql.ORDER_BY(sort);
        return sql.toString();
    }

    public String update() {
        SQL sql = new SQL();
        sql.UPDATE("certificates");
        sql.SET("name = #{name}, description=#{description}, price=#{price}, last_update_date=#{lastUpdateDate}, duration=#{duration}");
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    public String updatePrice() {
        SQL sql = new SQL();
        sql.UPDATE("certificates");
        sql.SET("price=#{price}, last_update_date=#{lastUpdateDate}");
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    private String sort(Map<String, Object> parameters) {
        SearchUtil searchUtil = new SearchUtil();
        String sort = "certificates.id";
        if (parameters.get("sort") != null) {
            sort = searchUtil.sort(parameters);
        }
        return sort;
    }
}