package com.epam.esm.mapper;

import com.epam.esm.entity.CertificateTagConnecting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CertificateTagConnectingMapper {

    @Insert("INSERT INTO connecting (certificate_id, tag_id) VALUES (#{certificateId},#{tagId})")
    void save(CertificateTagConnecting certificateTagConnecting);

    @Delete("DELETE FROM connecting WHERE certificate_id = #{certificateId}")
    void deleteConnect(CertificateTagConnecting certificateTagConnecting);

    @Update("INSERT INTO connecting (certificate_id, tag_id) VALUES (#{certificateId},#{tagId})")
    void updateConnect(CertificateTagConnecting certificateTagConnecting);
}