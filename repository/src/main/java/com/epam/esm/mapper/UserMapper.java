package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT id, password, roles, active, user_name FROM users WHERE user_name = #{name}")
    @Results(value = {
            @Result(property = "username", column = "user_name"),
            @Result(property = "role", column = "roles")
    })
    User findByUserName(String name);

    @Insert("INSERT IGNORE INTO users (active, password, roles, user_name) VALUES (true,#{password},'ROLE_USER',#{username})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(User user);

    @Select("SELECT tags.id, tags.name FROM tags INNER JOIN connecting ON tags.id=connecting.tag_id WHERE connecting.certificate_id = (SELECT certificate_id FROM purchases WHERE user_id = (SELECT user_id FROM purchases GROUP BY price HAVING MAX(price) ORDER BY price DESC LIMIT 1) GROUP BY certificate_id ORDER BY COUNT(certificate_id) DESC LIMIT 1) GROUP BY tag_id ORDER BY COUNT(tag_id) DESC LIMIT 1")
    Tag findMostPopularTag();
}