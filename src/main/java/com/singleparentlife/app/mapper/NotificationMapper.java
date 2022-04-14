package com.singleparentlife.app.mapper;

import com.singleparentlife.app.service.model.AppNotification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("INSERT INTO notification (userId, topic, title, content, time, isRead) " +
            "VALUES (#{userId}, #{topic}, #{title}, #{content}, #{time}, #{isRead})")
    @Options(useGeneratedKeys = true, keyProperty = "notificationId", keyColumn = "notificationId")
    int save(AppNotification appNotification);

    @Select("SELECT * FROM notification WHERE userId = #{userId}")
    @Results({
            @Result(id = true, column = "notificationId", property = "notificationId"),
            @Result(column = "userId", property = "userId"),
            @Result(column = "topic", property = "topic"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "time", property = "time"),
            @Result(column = "isRead", property = "isRead")
    })
    List<AppNotification> getAllNotificationOfUser(Long userId);

    @Select("SELECT * FROM notification WHERE notificationId = #{notificationId}")
    @Results({
            @Result(id = true, column = "notificationId", property = "notificationId"),
            @Result(column = "userId", property = "userId"),
            @Result(column = "topic", property = "topic"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "time", property = "time"),
            @Result(column = "isRead", property = "isRead")
    })
    AppNotification getNotificationById(Long notificationId);

    @Update("UPDATE notification SET isRead = #{isRead} WHERE notificationId = #{notificationId}")
    int markStatusOfNotificationById(Long notificationId, boolean isRead);

    @Update("UPDATE notification SET isRead = #{isRead} WHERE userId = #{userId}")
    int markStatusOfAllNotification(Long userId, boolean isRead);

    @Delete("DELETE FROM notification WHERE notificationId = #{notificationId}")
    int deleteNotificationById(Long notificationId);

    @Delete("DELETE FROM notification WHERE userId = #{userId}")
    int deleteAllNotification(Long userId);

}
