package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Role;
import com.singleparentlife.app.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /* Create */
    @Insert("INSERT INTO user (fireId, email, password, startDate, loginTime, roleId, isActive, isSuspended) VALUES " +
            "(#{fireId}, #{email}, #{password}, #{startDate}, #{loginTime}, #{roleId}, #{isActive}, #{isSuspended})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int save(User user);

    /* Read */
    //searching user table using email
    @Select("SELECT * FROM user WHERE email = #{email}")
    @Results ({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "fireId", column = "fireId"),
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

    //searching user table using userId
    @Select("SELECT * FROM user WHERE userId = #{userId}")
    @Results ({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "fireId", column = "fireId"),
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

    //searching user table using fireId
    @Select("SELECT * FROM user WHERE fireId = #{fireId}")
    @Results ({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "fireId", column = "fireId"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "password", column = "password"),
            @Result(property = "startDate", column = "startDate"),
            @Result(property = "loginTime", column = "loginTime"),
            @Result(property = "roleId", column = "roleId"),
            @Result(property = "isActive", column = "isActive"),
            @Result(property = "isSuspended", column = "isSuspended")

    })
    User findByFireId(String fireId);

    //Searching user table using phone number
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    @Results ({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "fireId", column = "fireId"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "password", column = "password"),
            @Result(property = "startDate", column = "startDate"),
            @Result(property = "loginTime", column = "loginTime"),
            @Result(property = "roleId", column = "roleId"),
            @Result(property = "isActive", column = "isActive"),
            @Result(property = "isSuspended", column = "isSuspended")
    })
    User findByPhone(String phone);

    long getUserIdByFireId(String fireId);

    /* Update */


    /* Delete */
}
