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

    @Insert("INSERT IGNORE INTO tags (name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    long save(Tag tag);

    @Delete("DELETE FROM tags WHERE id = #{id}")
    int delete(int id);

    @Select("SELECT EXISTS(SELECT name FROM tags WHERE name = #{name})")
    Boolean existByName(String name);

    @Select("SELECT tags.id, tags.name FROM tags INNER JOIN connecting ON tags.id=connecting.tag_id"
            + " WHERE connecting.certificate_id = " +
            "(SELECT certificate_id FROM purchases WHERE user_id = " +
            "(SELECT user_id FROM purchases where price =(select MAX(price) from purchases limit 1) ORDER BY price DESC LIMIT 1) GROUP BY certificate_id ORDER BY COUNT(certificate_id) DESC LIMIT 1) GROUP BY tag_id ORDER BY COUNT(tag_id) DESC LIMIT 1")
    Tag findMostPopularTag();

    @Select("SELECT id FROM tags where name = #{name}")
    long findIdTag(String name);
}