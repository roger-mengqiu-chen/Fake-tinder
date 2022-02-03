package com.singleparentlife.app.config.security;


import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.Role;
import com.singleparentlife.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    /**
     * We are using fireId as username here. Have to override the method. So can't change the method name
     */
    @Override
    public UserDetails loadUserByUsername(String fireId){

        User user = userMapper.findByFireId(fireId);

        if (user != null) {
            Role role = userMapper.getRoleOfUser(user);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            MyUserDetails userDetails = new MyUserDetails(user);
            userDetails.setAuthorities(authorities);

            return userDetails;
        }
        return null;
    }


}
