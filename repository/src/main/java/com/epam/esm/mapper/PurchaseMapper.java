package com.epam.esm.mapper;

import com.epam.esm.entity.Purchase;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface PurchaseMapper {
    @Insert("INSERT INTO purchases (user_id, certificate_id, price, date_purchase) VALUES (#{userId},#{certificateId},#{price},#{dateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void buy(Purchase purchase);
}