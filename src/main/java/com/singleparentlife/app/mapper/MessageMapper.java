package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface MessageMapper {

    @Insert("INSERT INTO message (senderId, receiverId, time, content) values " +
            "(#{senderId}, #{receiverId}, #{time}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    long save(Message message);
}