package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Profile;
import org.apache.ibatis.annotations.*;


@Mapper
public interface ProfileMapper {

    /* Create */
    @Insert("Insert INTO profile (userId, avatarId, firstname, lastname, age, gender, description, locationId) VALUES " +
    "(#{userId}, #{avatarId}, #{firstname}, #{lastname}, #{age}, #{gender}, #{description}, #{locationId})")
    int save(Profile profile);

    /* Read */
    //Searching using userId
    @Select("SELECT * FROM profile WHERE userId = #{userId}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "age"),
            @Result(property = "description", column = "description"),
            @Result(property = "locationId", column = "locationId")
    })
    //need to implement findByUserId in service class
    Profile findByUserId(long userId);

    //Searching using first name
    @Select("SELECT * FROM profile WHERE firstname = #{firstname}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "age"),
            @Result(property = "description", column = "description"),
            @Result(property = "locationId", column = "locationId")
    })
    //need to implement findByFirstname in service class
    Profile findByFirstname(String firstname);

    //Searching using last name
    @Select("SELECT * FROM profile WHERE lastname = #{lastname}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "age"),
            @Result(property = "description", column = "description"),
            @Result(property = "locationId", column = "locationId")
    })
    //need to implement findByLastname in service class
    Profile findByLastname(String lastname);

    //Searching using both first and last name
    @Select("SELECT * FROM profile WHERE firstname = #{firstname} AND lastname = #{lastname}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "age"),
            @Result(property = "description", column = "description"),
            @Result(property = "locationId", column = "locationId")
    })
    //need to implement findByFirstLastname in service class
    Profile findByFirstLastname(String firstname, String lastname);

    //Searching using gender
    @Select("SELECT * FROM profile WHERE gender = #{gender}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "age"),
            @Result(property = "description", column = "description"),
            @Result(property = "locationId", column = "locationId")
    })
    //need to implement findByGender in service class
    Profile findByGender(char gender);


    /* Update */
    //update firstname in profile table
    @Update("UPDATE profile SET firstname = #{firstname} WHERE userId = #{userId}")
    int updateFirstname(Profile profile);

    //update lastname in profile table
    @Update("UPDATE profile SET lastname = #{lastname} WHERE userId = #{userId}")
    int updateLastname(Profile profile);

    //update description in profile table
    @Update("UPDATE profile SET description = #{description} WHERE userId = #{userId}")
    int updateDescription(Profile profile);

    //update locationId in profile table
    @Update("UPDATE profile SET locationId = #{locationId} WHERE userId = #{userId}")
    int updateLocationId(Profile profile);

    //update age in profile table
    @Update("UPDATE profile SET age = #{age} WHERE userId = #{userId}")
    int updateAge(Profile profile);

    //update gender in profile table
    @Update("UPDATE profile SET gender = #{gender} WHERE userId = #{userId}")
    int updateGender(Profile profile);

    //update avatarId in profile table
    @Update("UPDATE profile SET avatarId = #{avatarId} WHERE userId = #{userId}")
    int updateAvatarId(Profile profile);


    /* Delete */
    //to delete a row within the profile table:
    @Delete("DELETE FROM profile WHERE userId = #{userId}")
    int delete(Profile profile);



}
