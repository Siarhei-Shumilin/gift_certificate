package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class PurchaseSqlUtil {

    public String findUsersPurchase() {
        SQL sql = new SQL();
        sql.SELECT("purchases.id, purchases.user_id, purchases.certificate_id, certificates.name,  certificates.description, purchases.price, purchases.date_purchase");
        sql.FROM("certificates");
        sql.INNER_JOIN("purchases ON purchases.certificate_id=certificates.id");
        sql.WHERE("user_id=#{userId}");
        return sql.toString();
    }
}