package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE email = #{email}")
    @Results ({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "password", column = "password"),
            @Result(property = "startDate", column = "startDate"),
            @Result(property = "loginTime", column = "loginTime"),
            @Result(property = "roleId", column = "roleId"),
            @Result(property = "isActive", column = "isActive"),
            @Result(property = "isSuspended", column = "isSuspended")

    })
    Optional<User> findByEmail(String email);

}
