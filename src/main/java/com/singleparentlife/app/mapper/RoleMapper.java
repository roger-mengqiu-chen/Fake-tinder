package com.singleparentlife.app.mapper;


import com.singleparentlife.app.model.Role;
import com.singleparentlife.app.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM role WHERE roleId = #{roleId}")
    @Results({
            @Result(id = true, property = "roleId", column = "roleId"),
            @Result(property = "roleName", column = "roleName")
    })
    Role getRoleOfUser(User user);
}
