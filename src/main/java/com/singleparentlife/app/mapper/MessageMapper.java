package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MessageMapper {

    @Insert("INSERT INTO message (senderId, receiverId, attachmentId, time, content) values " +
            "(#{senderId}, #{receiverId}, #{attachmentId}, #{time}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    long save(Message message);

    @Update("UPDATE message " +
            "SET attachmentId = #{attachmentId} " +
            "WHERE messageId = #{messageId}")
    int updateAttachmentId(Message message);
}
