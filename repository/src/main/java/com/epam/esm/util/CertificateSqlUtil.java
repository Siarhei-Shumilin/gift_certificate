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
        return new SQL() {{
            SELECT(selectData);
            FROM(table);
            INNER_JOIN(innerJoinCertificate);
            INNER_JOIN(innerJoinTag);
            if (parameters.get("name") != null) {
                WHERE(searchUtil.findByName(parameters));
            } else if (parameters.get("description") != null) {
                WHERE(searchUtil.findDescription(parameters));
            } else if (tagList != null) {
                WHERE(searchUtil.findByTag(tagList));
            }
            if (parameters.get("sort") != null) {
                ORDER_BY(searchUtil.sort(parameters));
            } else {
                ORDER_BY("certificates.id");
            }
        }}.toString();
    }

    public String update() {
        return new SQL() {{
            UPDATE("certificates");
            SET("name = #{name}, description=#{description}, price=#{price}, last_update_date=#{lastUpdateDate}, duration=#{duration}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String updatePrice() {
        return new SQL() {{
            UPDATE("certificates");
            SET("price=#{price}, last_update_date=#{lastUpdateDate}");
            WHERE("id = #{id}");
        }}.toString();
    }
}