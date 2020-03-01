package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.util.TagSqlUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TagMapper {

    @SelectProvider(type = TagSqlUtil.class, method = "getTagByParameter")
    List<Tag> findByParameters(String name, RowBounds rowBounds);

    @Select("SELECT id, name FROM tags WHERE id = #{tagId}")
    Tag findById(long tagId);

    @Insert("INSERT IGNORE INTO tags (name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    long save(Tag tag);

    @Insert({"<script>",
            "insert ignore into  tags (name) values ",
            "<foreach collection='list' item='tag' index='index' open='(' separator = '),(' close=')' >#{tag.name}</foreach>",
            "</script>"})
    void saveListTags(@Param("list") List<Tag> tag);

    @Delete("DELETE FROM tags WHERE id = #{id}")
    int delete(long id);

    @Select("SELECT tags.id, tags.name FROM tags INNER JOIN connecting ON tags.id=connecting.tag_id"
            + " WHERE connecting.certificate_id = " +
            "(SELECT certificate_id FROM purchases WHERE user_id = " +
            "(SELECT user_id FROM purchases GROUP BY user_id ORDER BY sum(price) DESC LIMIT 1)" +
            " GROUP BY certificate_id ORDER BY COUNT(certificate_id) DESC LIMIT 1) " +
            "GROUP BY tag_id ORDER BY COUNT(tag_id) DESC LIMIT 1")
    Tag findMostPopularTag();

    @Select({"<script>",
            "select id, name from tags where name in ",
            "<foreach collection='list' item='tag' index='index' open='(' separator = ',' close=')' >#{tag.name}</foreach>",
            "</script>"})
    List<Tag> findTagByName(@Param("list") List<Tag> listTagWithoutId);
}