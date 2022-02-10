package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Attachment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AttachmentMapper {
    /*
        ATTENTION: saveWithMessage is used when attachment doesn't have userId
     */
    @Insert("INSERT INTO attachment (messageId, attachmentType, attachmentContent) " +
            "values (#{messageId}, #{attachmentType}, #{attachmentContent})")
    @Options(useGeneratedKeys = true, keyProperty = "attachmentId")
    long saveWithMessage(Attachment attachment);

    /*
        ATTENTION: saveWithProfile is used when attachment doesn't have messageId
     */
    @Insert("INSERT INTO attachment (messageId, userId, attachmentType, attachmentContent) VALUES " +
            "(null, #{userId}, #{attachmentType}, #{attachmentContent})")
    @Options(useGeneratedKeys = true, keyProperty = "attachmentId")
    long saveWithProfile(Attachment attachment);

    @Select("SELECT * FROM attachment WHERE attachmentId = #{attachmentId}")
    @Results({
            @Result(id = true, property = "attachmentId", column = "attachmentId"),
            @Result(property = "attachmentType", column = "attachmentType"),
            @Result(property = "attachmentContent", column = "attachmentContent")
    })
    Attachment findById(long attachmentId);

    @Select("SELECT * FROM attachment WHERE messageId = #{messageId}")
    @Results({
            @Result(id = true, property = "attachmentId", column = "attachmentId"),
            @Result(property = "attachmentType", column = "attachmentType"),
            @Result(property = "attachmentContent", column = "attachmentContent")
    })
    Attachment findByMessageId(long messageId);
}
