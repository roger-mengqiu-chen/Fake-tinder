package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.EventInvitation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventInvitationMapper {
    /* Create */
    @Insert("INSERT INTO eventInvitation (eventId, targetUserId, reactionId) VALUES " +
            "(#{eventId}, #{targetUserId}, #{reactionId})")
    @Options(useGeneratedKeys = true, keyProperty = "eventInvitationId", keyColumn = "eventInvitationId")
    int save(EventInvitation eventInvitation);

    /* Read */
    /* Search by eventInvitationId */
    @Select("SELECT * FROM eventInvitation WHERE eventInvitationId = #{eventInvitationId}")
    @Results({
            @Result(id = true, property = "eventInvitationId", column = "eventInvitationId"),
            @Result(property = "eventId", column = "eventId"),
            @Result(property = "targetUserId", column = "targetUserId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    EventInvitation findByEventInvitationId(Long eventInvitationId);

    /* Search by eventId */
    @Select("SELECT * FROM eventInvitation WHERE eventId = #{eventId}")
    @Results({
            @Result(id = true, property = "eventInvitationId", column = "eventInvitationId"),
            @Result(property = "eventId", column = "eventId"),
            @Result(property = "targetUserId", column = "targetUserId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    List<EventInvitation> findByEventId(Long eventId);

    /* Search by targetUserId */
    @Select("SELECT * FROM eventInvitation WHERE targetUserId = #{targetUserId}")
    @Results({
            @Result(id = true, property = "eventInvitationId", column = "eventInvitationId"),
            @Result(property = "eventId", column = "eventId"),
            @Result(property = "targetUserId", column = "targetUserId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    List<EventInvitation> findByTargetUserId(Long targetUserId);

    /* Search by eventId and targetUserId  */
    @Select("SELECT * FROM eventInvitation WHERE eventId = #{eventId} AND targetUserId = #{targetUserId}")
    @Results({
            @Result(id = true, property = "eventInvitationId", column = "eventInvitationId"),
            @Result(property = "eventId", column = "eventId"),
            @Result(property = "targetUserId", column = "targetUserId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    EventInvitation findByEventIdAndTargetUserId(Long eventId, Long targetUserId);

    /* Search by reactionId */
    @Select("SELECT * FROM eventInvitation WHERE reactionId = #{reactionId}")
    @Results({
            @Result(id = true, property = "eventInvitationId", column = "eventInvitationId"),
            @Result(property = "eventId", column = "eventId"),
            @Result(property = "targetUserId", column = "targetUserId"),
            @Result(property = "reactionId", column = "reactionId")
    })
    List<EventInvitation> findByReactionId(Short reactionId);

    /* Update */
    @Update("UPDATE eventInvitation SET reactionId = #{reactionId} WHERE eventInvitationId = #{eventInvitationId}")
    int updateReactionId(Long eventInvitationId, Short reactionId);

    /* Delete */
    @Delete("DELETE FROM eventInvitation WHERE eventInvitationId = #{eventInvitationId}")
    int deleteByEventInvitationId(Long eventInvitationId);

}
