package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Attachment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AttachmentMapper {

    @Insert("INSERT INTO attachment (attachmentType, attachmentContent) " +
            "values (#{attachmentType}, #{attachmentContent})")
    @Options(useGeneratedKeys = true, keyProperty = "attachmentId")
    long save(Attachment attachment);


    @Select("SELECT * FROM attachment WHERE attachmentId = #{attachmentId}")
    @Results({
            @Result(id = true, property = "attachmentId", column = "attachmentId"),
            @Result(property = "attachmentType", column = "attachmentType"),
            @Result(property = "attachmentContent", column = "attachmentContent")
    })
    Attachment findById(long attachmentId);
}
