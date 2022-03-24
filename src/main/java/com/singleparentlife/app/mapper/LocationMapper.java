package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Location;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LocationMapper {

    /* Create */
    //This will insert a location into the location table
    @Insert("INSERT INTO location (lat, lon, country, province, city, street, postcode) VALUES " +
    "(#{lat}, #{lon}, #{country}, #{province}, #{city}, #{street}, #{postcode})")
    @Options(useGeneratedKeys = true, keyProperty = "locationId", keyColumn = "locationId")
    long save(Location location);


    /* Read */
    //This will use everything except the locationId to find a location object
    @Select("SELECT * FROM location WHERE country = #{country} AND province = #{province} AND city = #{city} " +
    "AND street = #{street} AND postcode = #{postcode}")
    @Results ({
            @Result(id = true, property = "locationId", column = "locationId"),
            @Result(property = "lat", column = "lat"),
            @Result(property = "lon", column = "lon"),
            @Result(property = "country",  column = "country"),
            @Result(property = "province", column = "province"),
            @Result(property = "city", column = "city"),
            @Result(property = "street", column = "street"),
            @Result(property = "postcode", column = "postcode")
    })
    Location find(Location location);

    //This will use locationId to find a location
    @Select("SELECT * FROM location WHERE locationId = #{locationId}")
    @Results ({
            @Result(id = true, property = "locationId", column = "locationId"),
            @Result(property = "lat", column = "lat"),
            @Result(property = "lon", column = "lon"),
            @Result(property = "country",  column = "country"),
            @Result(property = "province", column = "province"),
            @Result(property = "city", column = "city"),
            @Result(property = "street", column = "street"),
            @Result(property = "postcode", column = "postcode")
    })
    Location findById(Long locationId);

    /* Delete */
    //This will be used to delete a location from the location table
    @Delete("DELETE FROM location WHERE locationId = #{locationId)")
    int delete(Location location);

    /* Update */
    //This will be used to update a location in the location table using the locationId
    @Update("UPDATE location " +
            "SET country = #{country}, " +
            "lat = #{lat} " +
            "lon = #{lon} " +
            "province = #{province}, " +
            "city = #{city}, " +
            "street = #{street}, " +
            "postcode = #{postcode}  " +
            "WHERE locationId = #{locationId}")
    int update(Location location);
}
