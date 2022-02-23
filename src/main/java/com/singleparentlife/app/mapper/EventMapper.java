package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Event;
import com.singleparentlife.app.model.EventInvitation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventMapper {
    /* Get */
    @Select("SELECT * FROM event")
    @Results({
            @Result(id = true, property = "eventId", column = "eventId"),
            @Result(property = "eventName", column = "eventName"),
            @Result(property = "eventTime", column = "eventTime"),
            @Result(property = "locationId", column = "locationId"),
            @Result(property = "eventDescription", column = "eventDescription"),
            @Result(property = "eventLink", column = "eventLink"),
    })
    List<Event> getAll();

    @Select("SELECT * FROM event WHERE eventId = #{eventId}")
    @Results({
            @Result(id = true, property = "eventId", column = "eventId"),
            @Result(property = "eventName", column = "eventName"),
            @Result(property = "eventTime", column = "eventTime"),
            @Result(property = "locationId", column = "locationId"),
            @Result(property = "eventDescription", column = "eventDescription"),
            @Result(property = "eventLink", column = "eventLink"),
    })
    Event getByEventId(Long eventId);

    @Select("SELECT * FROM event WHERE locationId = #{locationId}")
    @Results({
            @Result(id = true, property = "eventId", column = "eventId"),
            @Result(property = "eventName", column = "eventName"),
            @Result(property = "eventTime", column = "eventTime"),
            @Result(property = "locationId", column = "locationId"),
            @Result(property = "eventDescription", column = "eventDescription"),
            @Result(property = "eventLink", column = "eventLink"),
    })
    List<Event> getByLocationId(Long locationId);

    /* Create */
    @Insert("INSERT INTO event (eventName, eventTime, locationId, eventDescription, eventLink) VALUES " +
            "(#{eventName}, #{eventTime}, #{locationId}, #{eventDescription}, #{eventLink})")
    @Options(useGeneratedKeys = true, keyProperty = "eventId")
    int save(Event event);

    /* Update */
    @Update("UPDATE event SET eventName = #{eventName}, eventTime = #{eventTime}, locationId = #{locationId}" +
            ", eventDescription = #{eventDescription}, eventLink = #{eventLink} WHERE eventId = #{eventId}")
    int update(Event event);

    /* Delete */
    @Delete("DELETE FROM event WHERE eventId = #{eventId}")
    int delete(long eventId);
}
