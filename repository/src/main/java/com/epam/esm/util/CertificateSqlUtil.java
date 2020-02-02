package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Parameters;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class CertificateSqlUtil {
    private final String selectData = "certificates.id, certificates.name, certificates.description, certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration";
    private final String table = "certificates";
    private final String innerJoinCertificate = "connecting ON connecting.certificate_id=certificates.id";
    private final String innerJoinTag = "tags ON connecting.tag_id=tags.id";
    private final static int PAGE_SIZE = 5;

    public String getByParameter(Parameters parameters) {
        int skip = (parameters.getPage() - 1) * 5;
        String pageSize = skip + "," + PAGE_SIZE;
        return new SQL() {{
            SELECT(selectData);
            FROM(table);
            INNER_JOIN(innerJoinCertificate);
            INNER_JOIN(innerJoinTag);
            JOIN("(SELECT id FROM certificates ORDER BY id LIMIT " + pageSize + ") as b ON b.id = certificates.id");
            if (parameters.getName() != null && parameters.getDescription() == null && parameters.getTagName() == null) {
                WHERE("certificates.name like '" + "%" + parameters.getName() + "%'" + sort(parameters));
            } else if (parameters.getName() == null && parameters.getDescription() != null && parameters.getTagName() == null) {
                WHERE("certificates.description like '" + "%" + parameters.getDescription() + "%'" + sort(parameters));
            } else if (parameters.getName() == null && parameters.getDescription() == null && parameters.getTagName() != null) {
                WHERE("tags.name = \"" + parameters.getTagName() + "\"" + sort(parameters));
            } else if (parameters.getName() == null && parameters.getDescription() == null && parameters.getTagName() == null && parameters.getSort() != null) {
                if (parameters.getSort().equalsIgnoreCase("date")) {
                    if (parameters.getTypeSort() != null) {
                        ORDER_BY("certificates.last_update_date " + parameters.getTypeSort());
                    } else {
                        ORDER_BY("certificates.last_update_date");
                    }
                } else if (parameters.getSort().equalsIgnoreCase("name")) {
                    if (parameters.getTypeSort() != null) {
                        ORDER_BY("certificates.name " + parameters.getTypeSort());
                    } else {
                        ORDER_BY("certificates.name");
                    }
                } else if (parameters.getName() == null && parameters.getDescription() != null && parameters.getTagName() != null) {
                    WHERE("certificates.description like '" + "%" + parameters.getDescription() + "%'" + " and tags.name = " + parameters.getTagName() + sort(parameters));
                } else if (parameters.getName() != null && parameters.getDescription() == null && parameters.getTagName() != null) {
                    WHERE("certificates.name like '" + "%" + parameters.getName() + "%'"
                            + " and tags.name = \"" + parameters.getTagName() + "\"" + sort(parameters));
                }
            }
        }}.toString();
    }

    public String sort(Parameters parameters) {
        String sort = "";
        if (parameters.getSort() != null) {
            if (parameters.getSort().equalsIgnoreCase("date")) {
                sort = " ORDER BY certificates.last_update_date";
            } else if (parameters.getSort().equalsIgnoreCase("name")) {
                sort = " ORDER BY certificates.name";
            }
            if (parameters.getTypeSort() != null && parameters.getTypeSort().equalsIgnoreCase("DESC")) {
                sort = sort + " DESC";
            }
        }
        return sort;
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
}