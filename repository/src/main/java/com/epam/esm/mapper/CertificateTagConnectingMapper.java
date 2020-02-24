package com.epam.esm.mapper;

import com.epam.esm.entity.CertificateTagConnecting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CertificateTagConnectingMapper {

    @Insert({"<script>",
            "insert into  connecting (certificate_id, tag_id) values ",
            "<foreach collection='list' item='connecting' index='index' open='(' separator = '),(' close=')' >#{connecting.certificateId},#{connecting.tagId}</foreach>",
            "</script>"})
    int save(@Param("list") List<CertificateTagConnecting> certificateTagConnecting);

    @Delete("DELETE FROM connecting WHERE certificate_id = #{certificateId}")
    void deleteConnect(CertificateTagConnecting certificateTagConnecting);
}