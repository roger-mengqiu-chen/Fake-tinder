package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Attachment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    /*
        ATTENTION: saveWithMessage is used when attachment doesn't have userId
     */
    @Insert("INSERT INTO attachment (messageId, attachmentType, attachmentContent) " +
            "values (#{messageId}, #{attachmentType}, #{attachmentContent})")
    @Options(useGeneratedKeys = true, keyProperty = "attachmentId", keyColumn = "attachmentId")
    long saveWithMessage(Attachment attachment);

    /*
        ATTENTION: saveWithProfile is used when attachment doesn't have messageId
     */
    @Insert("INSERT INTO attachment (messageId, userId, attachmentType, attachmentContent) VALUES " +
            "(null, #{userId}, #{attachmentType}, #{attachmentContent})")
    @Options(useGeneratedKeys = true, keyProperty = "attachmentId", keyColumn = "attachmentId")
    long saveWithProfile(Attachment attachment);

    /**
     * Find attachment by id
     * USE WITH CAUTION; CAN CAUSE LARGE MEMORY USAGE
     * @param attachmentId
     * @return
     */
    @Select("SELECT * FROM attachment WHERE attachmentId = #{attachmentId}")
    @Results({
            @Result(id = true, property = "attachmentId", column = "attachmentId"),
            @Result(property = "attachmentType", column = "attachmentType"),
            @Result(property = "attachmentContent", column = "attachmentContent")
    })
    Attachment findById(long attachmentId);

    @Select("SELECT attachmentId FROM attachment WHERE attachmentId = #{attachmentId}")
    Long findIdOfAttachment(long attachmentId);

    @Select("SELECT userId FROM attachment WHERE attachmentId = #{attachmentId}")
    Long getProfileIdOfAttachment(long attachmentId);

    @Select("SELECT * FROM attachment WHERE messageId = #{messageId}")
    @Results({
            @Result(id = true, property = "attachmentId", column = "attachmentId"),
            @Result(property = "attachmentType", column = "attachmentType"),
            @Result(property = "attachmentContent", column = "attachmentContent")
    })
    Attachment findByMessageId(long messageId);

    @Select("SELECT attachmentId FROM attachment WHERE userId = #{userId}")
    @Results({
            @Result(id = true, property = "attachmentId", column = "attachmentId"),
            @Result(property = "attachmentType", column = "attachmentType"),
            @Result(property = "attachmentContent", column = "attachmentContent")
    })
    List<Long> findByProfileId(long userId);

    @Update("UPDATE attachment " +
            "SET attachmentType = #{attachmentType}, " +
            "attachmentContent = #{attachmentContent} " +
            "WHERE attachmentId = #{attachmentId}")
    void updateAttachment(Attachment attachment);

    @Delete("DELETE FROM attachment " +
            "WHERE attachmentId = #{attachmentId}")
    void delete(Long attachmentId);
}
