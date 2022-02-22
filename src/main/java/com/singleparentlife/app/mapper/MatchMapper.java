package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Match;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MatchMapper {

    @Insert("INSERT INTO matches (userId, targetId, reactionId) VALUES " +
            "(#{userId}, #{targetId}, #{reactionId})")
    int save(Match match);

    @Select("SELECT * FROM matches " +
            "WHERE userId = #{userId} AND targetId = #{targetId}")
    @Results({
            @Result(property = "userId", column = "userId"),
            @Result(property = "targetId", column = "targetId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    Match findMatchBetweenUsers (Long userId, Long targetId);

    @Update("UPDATE matches (reactionId) VALUES (#{reactionId}) " +
            "WHERE userId = #{userId} AND targetId = #{targetId}")
    int update(Match match);
}
