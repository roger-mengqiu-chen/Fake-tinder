package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.ReportedUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportedUserMapper {
    
    
    int save(ReportedUser reportedUser);

    ReportedUser findById(Long userId);

    int delete(ReportedUser reportedUser);
}
