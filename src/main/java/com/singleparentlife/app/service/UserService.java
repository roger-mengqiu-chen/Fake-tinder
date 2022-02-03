package com.singleparentlife.app.service;

import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void processOAuthPostLogin(String email) {
        User existedUser = userMapper.findByEmail(email);
        if(existedUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword("password");
            newUser.setStartDate(LocalDate.now());
            newUser.setLoginTime(LocalDateTime.now());
            newUser.setRoleId(2);
            newUser.setActive(true);
            newUser.setSuspended(false);

            userMapper.save(newUser);
        }
    }

    public void saveUser(User user) {
        userMapper.save(user);
    }
}
