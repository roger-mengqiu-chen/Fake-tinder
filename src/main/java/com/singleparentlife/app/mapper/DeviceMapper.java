package com.singleparentlife.app.mapper;

import com.singleparentlife.app.service.model.Device;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceMapper {

    @Insert("INSERT INTO userDevice (userId, deviceToken, registerTime) VALUES (#{userId}, #{deviceToken}, #{registerTime})")
    @Options(useGeneratedKeys = true, keyProperty = "deviceId", keyColumn = "deviceId")
    int save(Device device);

    @Select("SELECT * FROM userDevice WHERE userId = #{userId}")
    @Results ({
            @Result(id = true, column = "deviceId", property = "deviceId"),
            @Result(column = "userId", property = "userId"),
            @Result(column = "deviceToken", property = "deviceToken"),
            @Result(column = "registerTime", property = "registerTime")
    })
    List<Device> getAllDevicesOfUser(Long userId);

    @Select("SELECT deviceToken FROM userDevice WHERE userId = #{userId}")
    List<String> getAllDeviceTokensOfUser(Long userId);

    @Select("SELECT * FROM userDevice WHERE deviceId = #{deviceId}")
    @Results ({
            @Result(id = true, column = "deviceId", property = "deviceId"),
            @Result(column = "userId", property = "userId"),
            @Result(column = "deviceToken", property = "deviceToken"),
            @Result(column = "registerTime", property = "registerTime")
    })
    Device getDeviceById(Long deviceId);

    @Select("SELECT * FROM userDevice WHERE deviceToken = #{deviceToken}")
    @Results ({
            @Result(id = true, column = "deviceId", property = "deviceId"),
            @Result(column = "userId", property = "userId"),
            @Result(column = "deviceToken", property = "deviceToken"),
            @Result(column = "registerTime", property = "registerTime")
    })
    Device getDeviceByToken(String deviceToken);

    @Delete("DELETE FROM userDevice WHERE deviceId = #{deviceId}")
    int deleteDeviceById(Long deviceId);

    @Delete("DELETE FROM userDevice WHERE userId = #{userId}")
    int deleteAllDevicesOfUser(Long userId);

    @Delete("DELETE FROM userDevice WHERE deviceId = #{deviceId}")
    int deleteDevice(Device device);
}
