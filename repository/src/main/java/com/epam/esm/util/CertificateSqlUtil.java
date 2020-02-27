package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CertificateSqlUtil {
    private static final String SELECT_DATA = "certificates.id, certificates.name, certificates.description, certificates.price, " +
            "certificates.create_date, certificates.last_update_date, certificates.duration";
    private static final String TABLE = "certificates";
    private static final String INNER_JOIN_CERTIFICATE = "connecting ON connecting.certificate_id=certificates.id";
    private static final String INNER_JOIN_TAG = "tags ON connecting.tag_id=tags.id";

    public String getByParameter(Map<String, Object> parameters, List<String> tagList) {
        SearchUtil searchUtil = new SearchUtil();
        SQL sql = new SQL();
        sql.SELECT_DISTINCT(SELECT_DATA);
        sql.FROM(TABLE);
        sql.INNER_JOIN(INNER_JOIN_CERTIFICATE);
        sql.INNER_JOIN(INNER_JOIN_TAG);
        if (parameters.get("name") != null) {
            sql.WHERE(searchUtil.findByName(parameters, tagList, sql));
        } else if (parameters.get("description") != null) {
            sql.WHERE(searchUtil.findDescription(parameters, tagList, sql));
        } else if (tagList != null && !tagList.isEmpty()) {
            sql.WHERE(searchUtil.findByTag(tagList, sql));
        }
        String sort = searchUtil.sort(parameters);
        sql.ORDER_BY(sort);
        return sql.toString();
    }

    public String update() {
        SQL sql = new SQL();
        sql.UPDATE(TABLE);
        sql.SET("name = #{name}, description=#{description}, price=#{price}, last_update_date=#{lastUpdateDate}, duration=#{duration}");
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    public String updatePrice() {
        SQL sql = new SQL();
        sql.UPDATE(TABLE);
        sql.SET("price=#{price}, last_update_date=#{lastUpdateDate}");
        sql.WHERE("id = #{id}");
        return sql.toString();
    }
}