package com.epam.esm.util;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class PurchaseSqlUtil {

    public String findUsersPurchase() {
        SQL sql = new SQL();
        sql.SELECT("id, user_id, certificate_id, price, date_purchase");
        sql.FROM("purchases");
        sql.WHERE("user_id=#{userId}");
        return sql.toString();
    }
}