package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Reaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionMapper {
    /* Only has findByName */

    @Select("SELECT * FROM reaction WHERE reactionType = #{reactionType")
    @Results({
            @Result(id = true, property = "reactionId", column = "reactionId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    Reaction findByName(String reactionName);
}
