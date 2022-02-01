package com.singleparentlife.app.config.security;


import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.Role;
import com.singleparentlife.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userMapper.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("Couldn't find user");
        }

        Role role = userMapper.getRoleOfUser(user);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        MyUserDetails userDetails = new MyUserDetails(user);
        userDetails.setAuthorities(authorities);

        return userDetails;
    }
}
