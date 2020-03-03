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
            "<foreach collection='list' item='tag' index='index' open='(' separator = '),(' close=')' >" +
                    "#{tag.name}</foreach>",
            "</script>"})
    void saveListTags(@Param("list") List<Tag> tag);

    @Delete("DELETE FROM tags WHERE id = #{id}")
    int delete(long id);

    @Select("select tags.id, tags.name from " +
            "(select users.id as userId, sum(purchases.price) as sumPurchases " +
            "from purchases join users on users.id = purchases.user_id " +
            "group by users.id order by sumPurchases desc limit 1) as user_with_max_cost_purchases " +
            "join purchases on purchases.user_id = userId " +
            "join certificates on purchases.certificate_id = certificates.id " +
            "join connecting on certificates.id = connecting.certificate_id " +
            "join tags  on tags.id = connecting.tag_id " +
            "group by tags.id, tags.name order by sum(sumPurchases) desc limit 1")
    Tag findMostPopularTag();

    @Select({"<script>",
            "select id, name from tags where name in ",
            "<foreach collection='list' item='tag' index='index' open='(' separator = ',' close=')' >" +
                    "#{tag.name}</foreach>",
            "</script>"})
    List<Tag> findTagByName(@Param("list") List<Tag> listTagWithoutId);
}