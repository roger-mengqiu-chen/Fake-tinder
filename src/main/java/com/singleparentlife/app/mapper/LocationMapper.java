package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Location;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocationMapper {


    Location find(Location location);

    int save(Location location);

    Location findById(long locationId);

    int delete(Location location);

    int update(Location location);
}
