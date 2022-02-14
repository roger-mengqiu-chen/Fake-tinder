package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Preference;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PreferenceMapper {

    /*Create */
    //This will be used to add a  preference row to the preference table
    @Insert("INSERT INTO preference (content) VALUES (#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "preferenceId", keyColumn = "preferenceId")
    long save(Preference preference);

    //This will save a preferenceId and userId to the bridging table userPreference
    @Insert("INSERT INTO userPreference (userId, preferenceId) VALUES (#{userId}, #{preferenceId})")
    long savePreferenceForUser(long userId, long preferenceId);

    //This will save a userId and preferenceId to the userTag table
    @Insert("INSERT INTO userTag (userId, preferenceId) VALUES (#{userId}, #{preferenceId})")
    long saveTagForUser(long userId, long preferenceId);

    /* Read */
    //This will find a preference based on the content of the preference
    @Select("SELECT * FROM preference WHERE content = #{content}")
    @Results ({
            @Result(id = true, property = "preferenceId", column = "preferenceId"),
            @Result(property = "content", column = "content")
    })
    Preference findByContent(String content);

    //This will find a preference by using the preferenceId
    @Select("SELECT * FROM preference WHERE preferenceId = #{preferenceId}")
    @Results ({
            @Result(id = true, property = "preferenceId", column = "preferenceId"),
            @Result(property = "content", column = "content")
    })
    Preference findById(long preferenceId);

    /* Update */
    //This will update a preference in the preference table
    @Update("UPDATE preference SET content = #{content} WHERE preferenceId = #{preferenceId")
    int update(Preference preference);

    /* Delete */
    //This will delete a preference from the preference table
    @Delete("DELETE FROM preference WHERE preferenceId = #{preferenceId}")
    int deletePreference(Preference preference);

    //This will delete a user preference from the userPreference table
    @Delete("DELETE FROM userPreference WHERE userId = #{userId} AND preferenceId = #{preferenceId}")
    int deleteUserPreference(long userId, long preferenceId);

    //This will delete a user tag from the userTag table
    @Delete("DELETE FROM userTag WHERE userID = #{userId} AND preferenceId = #{preferenceId")
    int deleteUserTag(long userId, long preferenceId);

}
