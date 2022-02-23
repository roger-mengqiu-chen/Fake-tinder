package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /* Create */
    @Insert("INSERT INTO user (fireId, email, startDate, loginTime, roleId, isActive, isSuspended) VALUES " +
            "(#{fireId}, #{email}, #{startDate}, #{loginTime}, #{roleId}, #{isActive}, #{isSuspended})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "userId")
    long save(User user);

    /* Read */
    @Select("SELECT * FROM user")
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
    List<User> findAll();

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
            @Result(property = "startDate", column = "startDate"),
            @Result(property = "loginTime", column = "loginTime"),
            @Result(property = "roleId", column = "roleId"),
            @Result(property = "isActive", column = "isActive"),
            @Result(property = "isSuspended", column = "isSuspended")

    })
    User findById(Long userId);

    //searching user table using fireId
    @Select("SELECT * FROM user WHERE fireId = #{fireId}")
    @Results ({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "fireId", column = "fireId"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
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
            @Result(property = "startDate", column = "startDate"),
            @Result(property = "loginTime", column = "loginTime"),
            @Result(property = "roleId", column = "roleId"),
            @Result(property = "isActive", column = "isActive"),
            @Result(property = "isSuspended", column = "isSuspended")
    })
    User findByPhone(String phone);

    //This will find a user by using the fireId
    @Select("SELECT userId FROM user WHERE fireId = #{fireId}")
    Long getUserIdByFireId(String fireId);

    /* Update */
    //This will update any changes to a user row in the user table
    @Update("UPDATE user SET fireId = #{fireId}, email = #{email}, phone = #{phone}, " +
            "startDate = #{startDate}, loginTime = #{loginTime}, roleId = #{roleId}, isActive = #{isActive}, isSuspended = #{isSuspended} " +
            "WHERE userId = #{userId}")
    int update(User user);


    /* Delete */
    //This will delete a user row from the user table usign the userId
    @Delete("DELETE FROM user WHERE userId = #{userId}")
    int delete(Long UserId);


}
