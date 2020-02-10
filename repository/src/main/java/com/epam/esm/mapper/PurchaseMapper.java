package com.epam.esm.mapper;

import com.epam.esm.entity.Purchase;
import com.epam.esm.util.PurchaseSqlUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    @Insert("INSERT INTO purchases (user_id, certificate_id, price, date_purchase) VALUES (#{userId},#{certificateId},#{price},#{dateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(Purchase purchase);

    @SelectProvider(type = PurchaseSqlUtil.class, method = "findUsersPurchase")
    @Results(value = {
            @Result(property = "certificatesName", column = "name"),
            @Result(property = "certificateId", column = "certificate_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "certificateDescription", column = "description"),
            @Result(property = "dateTime", column = "date_purchase"),
    })
    List<Purchase> findUsersPurchases(long userId, RowBounds rowBounds);
}