package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Role;
import com.singleparentlife.app.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /* Create */
    @Insert("INSERT INTO user (email, password, startDate, loginTime, roleId, isActive, isSuspended) VALUES " +
            "(#{email}, #{password}, #{startDate}, #{loginTime}, #{roleId}, #{isActive}, #{isSuspended})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int save(User user);

    /* Read */

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
    User findByEmail(String email);

    @Select("SELECT * FROM user WHERE userId = #{userId}")
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
    User findById(long userId);

    @Select("SELECT * FROM role WHERE roleId = #{roleId}")
    Role getRoleOfUser(User user);
}
