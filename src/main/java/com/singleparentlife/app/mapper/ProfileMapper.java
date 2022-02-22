package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Profile;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ProfileMapper {

    /* Create */
    @Insert("Insert INTO profile " +
            "(userId, firstname, lastname, birthday, gender, showme, " +
            "description, company, jobTitle, school, locationId) " +
            "VALUES " +
            "(#{userId}, #{firstname}, #{lastname}, #{birthday}, #{gender}, #{showme}, " +
            "#{description}, #{company}, #{jobTitle}, #{school}, #{locationId})")
    long save(Profile profile);

    /* Read */
    @Select("SELECT * FROM profile")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "showme", column = "showme"),
            @Result(property = "description", column = "description"),
            @Result(property = "company", column = "company"),
            @Result(property = "school", column = "school"),
            @Result(property = "jobTitle", column = "jobTitle"),
            @Result(property = "locationId", column = "locationId")
    })
    List<Profile> findAll();
    //Searching using userId
    @Select("SELECT * FROM profile WHERE userId = #{userId}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "showme", column = "showme"),
            @Result(property = "description", column = "description"),
            @Result(property = "company", column = "company"),
            @Result(property = "school", column = "school"),
            @Result(property = "jobTitle", column = "jobTitle"),
            @Result(property = "locationId", column = "locationId")
    })
    //need to implement findByUserId in service class
    Profile findByUserId(Long userId);

    //Searching using first name
    @Select("SELECT * FROM profile WHERE firstname = #{firstname}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "avatarId", column = "avatarId"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "showme", column = "showme"),
            @Result(property = "description", column = "description"),
            @Result(property = "company", column = "company"),
            @Result(property = "school", column = "school"),
            @Result(property = "jobTitle", column = "jobTitle"),
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
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "showme", column = "showme"),
            @Result(property = "description", column = "description"),
            @Result(property = "company", column = "company"),
            @Result(property = "school", column = "school"),
            @Result(property = "jobTitle", column = "jobTitle"),
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
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "showme", column = "showme"),
            @Result(property = "description", column = "description"),
            @Result(property = "company", column = "company"),
            @Result(property = "school", column = "school"),
            @Result(property = "jobTitle", column = "jobTitle"),
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
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "showme", column = "showme"),
            @Result(property = "description", column = "description"),
            @Result(property = "company", column = "company"),
            @Result(property = "school", column = "school"),
            @Result(property = "jobTitle", column = "jobTitle"),
            @Result(property = "locationId", column = "locationId")
    })
    //need to implement findByGender in service class
    Profile findByGender(char gender);


    /* Update */
    @Update("UPDATE profile " +
            "SET " +
            "   firstname = #{firstname}, " +
            "   lastname = #{lastname}, " +
            "   gender = #{gender}, " +
            "   showme = #{showme}, " +
            "   description = #{description}, " +
            "   company = #{company}, " +
            "   jobTitle = #{jobTitle}, " +
            "   school = #{school}, " +
            "   locationId = #{locationId} " +
            "WHERE userId = #{userId}")
    int update(Profile profile);

    @Update("UPDATE profile SET profileImgAmt = #{profileImgAmt} WHERE userId = #{userId}")
    int updateProfileImgAmt(Profile profile);

    @Update("UPDATE profile SET avatarId = #{avatarId} WHERE userId = #{userId}")
    int updateProfileAvatarId(Profile profile);

    /* Delete */
    //to delete a row within the profile table:
    @Delete("DELETE FROM profile WHERE userId = #{userId}")
    int delete(Profile profile);

}
