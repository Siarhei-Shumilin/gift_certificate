package com.epam.esm.mapper;

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
}