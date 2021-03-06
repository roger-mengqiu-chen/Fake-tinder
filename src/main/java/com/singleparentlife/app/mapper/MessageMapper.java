package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Insert("INSERT INTO message (senderId, receiverId, attachmentId, time, content) values " +
            "(#{senderId}, #{receiverId}, #{attachmentId}, #{time}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId", keyColumn = "messageId")
    long save(Message message);

    @Select("SELECT * FROM message WHERE messageId = #{messageId}")
    @Results({
            @Result(id = true, property = "messageId", column = "messageId"),
            @Result(property = "senderId", column = "senderId"),
            @Result(property = "receiverId", column = "receiverId"),
            @Result(property = "attachmentId", column = "attachmentId"),
            @Result(property = "time", column = "time"),
            @Result(property = "content", column = "content")
    })
    Message getMessageById(Long messageId);

    @Select("SELECT * FROM message WHERE senderId = #{user1} AND receiverId = #{user2} OR senderId = #{user2} AND receiverId = #{user1} " +
            "ORDER by time")
    @Results({
            @Result(id = true, property = "messageId", column = "messageId"),
            @Result(property = "senderId", column = "senderId"),
            @Result(property = "receiverId", column = "receiverId"),
            @Result(property = "attachmentId", column = "attachmentId"),
            @Result(property = "time", column = "time"),
            @Result(property = "content", column = "content")
    })
    List<Message> getCombinedMessage(long user1, long user2);

    @Select("SELECT * FROM message WHERE receiverId = #{userId} OR senderId = #{userId} ORDER BY time DESC")
    @Results({
            @Result(id = true, property = "messageId", column = "messageId"),
            @Result(property = "senderId", column = "senderId"),
            @Result(property = "receiverId", column = "receiverId"),
            @Result(property = "attachmentId", column = "attachmentId"),
            @Result(property = "time", column = "time"),
            @Result(property = "content", column = "content")
    })
    List<Message> getAllUserMessage(long userId);

    @Update("UPDATE message " +
            "SET attachmentId = #{attachmentId} " +
            "WHERE messageId = #{messageId}")
    int updateAttachmentId(Message message);



    @Delete("DELETE FROM message WHERE messageId = #{messageId}")
    int delete(Message message);

    @Delete("DELETE FROM message WHERE senderId = #{userId} AND receiverId = #{userId}")
    int deleteAll(long userId);

}
