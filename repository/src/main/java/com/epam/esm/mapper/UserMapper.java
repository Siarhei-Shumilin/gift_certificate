package com.epam.esm.mapper;

import com.epam.esm.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT id, password, roles, active, user_name FROM users WHERE user_name = #{name}")
    @Results(value = {
            @Result(property = "username", column = "user_name"),
            @Result(property = "role", column = "roles")
    })
    User findByUserName(String name);
}