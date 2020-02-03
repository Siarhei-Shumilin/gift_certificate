package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Parameters;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class CertificateSqlUtil {
    private final String selectData = "certificates.id, certificates.name, certificates.description, "
        + "certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration";
    private final String table = "certificates";
    private final String innerJoinCertificate = "connecting ON connecting.certificate_id=certificates.id";
    private final String innerJoinTag = "tags ON connecting.tag_id=tags.id";
    private final static int PAGE_SIZE = 5;

    public String getByParameter(Parameters parameters) {
        Integer page = parameters.getPage();
        if (page == null) { page = 1; }
        String pageSize = ((page - 1) * 5) + "," + PAGE_SIZE;
        return buildQuery(parameters, pageSize);
    }

    public String update(GiftCertificate giftCertificate) {
        return new SQL() {{
            UPDATE("certificates");
            if (giftCertificate.getName() != null && giftCertificate.getDescription() != null
                    && giftCertificate.getPrice() != null && giftCertificate.getDuration() != 0 && giftCertificate.getTagList() != null) {
                SET("name = #{name}, description=#{description}, price=#{price}, last_update_date=#{lastUpdateDate}, duration=#{duration}");
            } else {
                SET("price=#{price}, last_update_date=#{lastUpdateDate}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    private String buildQuery(Parameters parameters, String pageSize){
        String limitSubQuery = new SQL(){{
        SELECT("id");
        FROM("certificates");
        ORDER_BY("id");
        LIMIT(pageSize);
        }}.toString();


        SearchUtil searchUtil = new SearchUtil();
        return new SQL() {{
            SELECT(selectData);
            FROM(table);
            INNER_JOIN(innerJoinCertificate);
            INNER_JOIN(innerJoinTag);
            JOIN("("+limitSubQuery + ") as b ON b.id = certificates.id");
            if (parameters.getName() != null) {
                WHERE(searchUtil.findByName(parameters));
            } else if (parameters.getDescription() != null) {
                WHERE(searchUtil.findDescription(parameters));
            } else if (parameters.getTagName() != null) {
                WHERE(searchUtil.findByTag(parameters));
            } else if (parameters.getSort() != null) {
                ORDER_BY(searchUtil.sort(parameters));
            }
        }}.toString();
    }
}