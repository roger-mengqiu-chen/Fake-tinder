package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Match;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * Find out all match created by this user, and reaction is not reject
     * @param userId
     * @return
     */
    @Select("SELECT * FROM matches " +
            "WHERE userId = #{userId} AND reactionId > 1")
    @Results({
            @Result(property = "userId", column = "userId"),
            @Result(property = "targetId", column = "targetId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    List<Match> findMatchOfUser (Long userId);

    /**
     * Find out all matches with this user, and reaction is not reject
     * @param userId
     * @return
     */
    @Select("SELECT * FROM matches " +
            "WHERE targetId = #{userId} AND reactionId >1")
    @Results({
            @Result(property = "userId", column = "userId"),
            @Result(property = "targetId", column = "targetId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    List<Match> findMatchWithUser(Long userId);

    /**
     * Find out successful match of user
     * @param userId
     * @return
     */
    @Select("SELECT a.userId, a.targetId, a.reactionId FROM matches a INNER JOIN matches b " +
            "ON a.userId = b.targetId AND b.userId = a.targetId" +
            "AND a.userId = #{userId} AND a.reactionId > 1 AND b.reactionId > 1")
    @Results({
            @Result(property = "userId", column = "userId"),
            @Result(property = "targetId", column = "targetId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    List<Match> findOutMatchOfUser(Long userId);

    /**
     * Find out failed matches of user
     * @param userId
     * @return
     */
    @Select("SELECT a.userId, a.targetId, a.reactionId FROM matches a INNER JOIN matches b " +
            "ON a.userId = b.targetId AND b.userId = a.targetId" +
            "AND a.targetId = #{userId} AND a.reactionId > 1 AND b.reactionId < 2;")
    @Results({
            @Result(property = "userId", column = "userId"),
            @Result(property = "targetId", column = "targetId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    List<Match> findFailedMatchOfUser(Long userId);

    @Update("UPDATE matches (reactionId) VALUES (#{reactionId}) " +
            "WHERE userId = #{userId} AND targetId = #{targetId}")
    int update(Match match);

    @Delete("DELETE FROM matches WHERE userId = #{userId} AND targetId = #{targetId}")
    int delete(Match match);
}
