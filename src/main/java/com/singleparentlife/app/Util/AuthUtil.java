package com.singleparentlife.app.Util;

import com.singleparentlife.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    private UserMapper userMapper;

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fireId = authentication.getName();
        return userMapper.getUserIdByFireId(fireId);
    }
}
