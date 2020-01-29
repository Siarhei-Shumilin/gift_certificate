package com.epam.esm.mapper;

import com.epam.esm.entity.CertificateTagConnecting;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Parameters;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.CertificateSqlUtil;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CertificateMapperMyBatis {
    @Insert("INSERT INTO certificates (name, description, price, create_date, last_update_date, duration) "
            + "VALUES (#{name},#{description},#{price},#{createDate},#{lastUpdateDate},#{duration})")
    void save(GiftCertificate giftCertificate);

    @Select("SELECT id FROM certificates WHERE name = #{name}")
    int getId(String name);

    @Delete("DELETE FROM certificates WHERE id = #{id}")
    boolean delete(int id);

    @SelectProvider(type = CertificateSqlUtil.class, method = "getTagByParameter")
    @Results(value = {
            @Result(property = "createDate", column = "create_date"),
            @Result(property="lastUpdateDate", column = "last_update_date"),
    })
    List<GiftCertificate> findByParameters(Parameters parameters);

    @Select("SELECT tags.id, tags.name FROM connecting INNER JOIN certificates ON connecting.certificate_id=certificates.id "
            + "INNER JOIN tags ON connecting.tag_id=tags.id WHERE connecting.certificate_id = #{certificateId}")
    List<Tag> findCertificateTags(CertificateTagConnecting certificateTagConnecting);

    @Update("UPDATE certificates SET name = #{name}, description=#{description}, price=#{price}, last_update_date=#{lastUpdateDate}, duration=#{duration} WHERE id = #{id}")
    void update(GiftCertificate giftCertificate);
}
