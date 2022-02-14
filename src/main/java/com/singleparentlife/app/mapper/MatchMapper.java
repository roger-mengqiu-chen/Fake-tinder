package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Match;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchMapper {

    @Insert("INSERT INTO matches (userId, targetId, reactionId) + VALUES " +
            "(#{userId}, #{targetId}, #{reactionId})")
    long save(Match match);
}
