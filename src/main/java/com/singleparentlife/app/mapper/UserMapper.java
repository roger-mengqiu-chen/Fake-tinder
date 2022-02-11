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
    long save(User user);

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

    //This will find a user by using the fireId
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
    long getUserIdByFireId(String fireId);

    /* Update */
    //This will update any changes to a user row in the user table
    @Update("UPDATE user SET fireId = #{fireId}, email = #{email}, phone = #{phone}, password = #{password}, " +
            "startDate = #{startDate}, loginTime = #{loginTime}, roleId = #{roleId}, isActive = #{isActive}, isSuspended = #{isSuspended} " +
            "WHERE userId = #{userId}")
    int update(User user);


    /* Delete */
    //This will delete a user row from the user table usign the userId
    @Delete("DELETE FROM user WHERE userId = #{userId}")
    int delete(Long UserId);

}
