package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.ReportedUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportedUserMapper {
    
    
    int save(ReportedUser reportedUser);

    List<ReportedUser> findById(long userId);

    int deletebyId(long userId);
}
