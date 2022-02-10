package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.ReportedUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReportedUserMapper {
    
    /* Create */
    //This is to insert a user that has been reported
    @Insert("INSERT INTO reportedUser (userId, reporterId, reason, reportTime) VALUES " +
    "(#{userId}, #{reporterId}, #{reason}, #{reportTime}")
    int save(ReportedUser reportedUser);

    /* Read */
    //Searching reportedUser table using the userId that was reported
    @Select("SELECT * FROM reportedUser WHERE userId = #{userId}")
    @Results (value = {
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "reporterId", column = "reporterId"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "reportTime", column = "reportTime")

    })
    List<ReportedUser> findById(long userId);


    /* Delete */
    //This is intended to delete, this will delete every report involving this user
    @Delete("DELETE FROM reportedUSer WHERE userId = #{userId}")
    int delete(ReportedUser reportedUser);

    //This will find all reports made by a certain reporterId
    @Select("SELECT * FROM reportedUser WHERE reportedId = #{reporterId}")
    @Results (value = {
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "reporterId", column = "reporterId"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "reportTime", column = "reportTime")

    })
    List<ReportedUser> findByReporterId(long reporterId);




}
