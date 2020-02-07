package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class PurchaseSqlUtil {

    public String findUsersPurchase() {
        return new SQL() {
            {
                SELECT("purchases.id, purchases.user_id, purchases.certificate_id, certificates.name,  certificates.description, purchases.price, purchases.date_purchase");
                FROM("certificates");
                INNER_JOIN("purchases ON purchases.certificate_id=certificates.id");
                WHERE("user_id=#{userId}");
            }
        }.toString();
    }
}