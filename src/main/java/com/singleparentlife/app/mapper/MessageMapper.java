package com.singleparentlife.app.mapper;

import com.singleparentlife.app.service.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * Insert message into database
     * @param message
     * @return long to confirm that it was entered to database
     */
    @Insert("INSERT INTO message (senderId, receiverId, attachmentId, time, content) values " +
            "(#{senderId}, #{receiverId}, #{attachmentId}, #{time}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId", keyColumn = "messageId")
    long save(Message message);

    /**
     * Retrieve a message from the database based on userId
     * @param messageId
     * @return Message found from userId
     */
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

    /**
     * Get the message history from between two users
     * @param user1
     * @param user2
     * @return List<message> containing the messages of the two users
     */
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

    /**
     * Get all the message history of a certain user
     * @param userId
     * @return List<message> containing all the users messages
     */
    @Select("SELECT * FROM message WHERE senderId = #{userId) OR receiverId = #{userId} ORDER BY time")
    @Results({
            @Result(id = true, property = "messageId", column = "messageId"),
            @Result(property = "senderId", column = "senderId"),
            @Result(property = "receiverId", column = "receiverId"),
            @Result(property = "attachmentId", column = "attachmentId"),
            @Result(property = "time", column = "time"),
            @Result(property = "content", column = "content")
    })
    List<Message> getAllUserMessage(long userId);

    /**
     * Update the attachment of a certain message
     * @param message
     * @return int to confirm update was successful
     */
    @Update("UPDATE message " +
            "SET attachmentId = #{attachmentId} " +
            "WHERE messageId = #{messageId}")
    int updateAttachmentId(Message message);


    /**
     * Delete message from database based on messageId
     * @param message
     * @return int to confirm deletion was successful
     */
    @Delete("DELETE FROM message WHERE messageId = #{messageId}")
    int delete(Message message);

    /**
     * Delete message from database based on the ID of the sender and reciver
     * @param userId
     * @return int to confirm deletion was successful
     */
    @Delete("DELETE FROM message WHERE senderId = #{userId} AND receiverId = #{userId}")
    int deleteAll(long userId);

}
