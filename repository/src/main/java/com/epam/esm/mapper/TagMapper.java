package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.util.TagSqlUtil;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {

    @SelectProvider(type = TagSqlUtil.class, method = "getTagByParameter")
    List<Tag> findByParameters(String name);

    @Insert("INSERT IGNORE INTO tags (name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    long save(Tag tag);

    @Delete("DELETE FROM tags WHERE id = #{id}")
    int delete(int id);

    @Select("SELECT EXISTS(SELECT name FROM tags WHERE name = #{name})")
    Boolean existByName(String name);
}