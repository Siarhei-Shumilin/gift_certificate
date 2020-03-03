package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.CertificateSqlUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface CertificateMapper {
    @Insert("INSERT INTO certificates (name, description, price, create_date, last_update_date, duration) " +
            "VALUES (#{name},#{description},#{price},#{createDate},#{lastUpdateDate},#{duration})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(GiftCertificate giftCertificate);

    @Delete("DELETE FROM certificates WHERE id = #{id}")
    int delete(long id);

    @SelectProvider(type = CertificateSqlUtil.class, method = "getByParameter")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "lastUpdateDate", column = "last_update_date"),
            @Result(property = "tagList", javaType = List.class, column = "id",
                    many = @Many(select = "findCertificateTags"))
    })
    List<GiftCertificate> findByParameters(Map<String, Object> parameters, List<String> tagList, RowBounds rowBounds);

    @Select("SELECT id, name, description, price, create_date, last_update_date, duration" +
            " FROM certificates WHERE id = #{certificateId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "lastUpdateDate", column = "last_update_date"),
            @Result(property = "tagList", javaType = List.class, column = "id",
                    many = @Many(select = "findCertificateTags"))
    })
    GiftCertificate findById(long certificateId);

    @Select("SELECT tags.id, tags.name FROM connecting " +
            "INNER JOIN certificates ON connecting.certificate_id=certificates.id " +
            "INNER JOIN tags ON connecting.tag_id=tags.id WHERE connecting.certificate_id = #{certificateId}")
    List<Tag> findCertificateTags(long certificateId);

    @UpdateProvider(type = CertificateSqlUtil.class, method = "update")
    int update(GiftCertificate giftCertificate);

    @UpdateProvider(type = CertificateSqlUtil.class, method = "updatePrice")
    int updatePrice(GiftCertificate giftCertificate);

    @Select("SELECT EXISTS(SELECT price FROM certificates WHERE id = #{id})")
    boolean existById(long id);
}