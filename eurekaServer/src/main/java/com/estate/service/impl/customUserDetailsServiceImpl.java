package com.estate.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.estate.mapper.user.TUserMapper;
import com.estate.model.SecurityUser;
import com.estate.model.user.TUser;
import com.estate.model.user.TUserExample;
import com.estate.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class customUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private TUserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("userService"+s);
        TUserExample userExample = new TUserExample();
        userExample.createCriteria().andUserNameEqualTo(s);
        List<TUser> users =userMapper.selectByExample(userExample);
        SecurityUser securityUser = new SecurityUser(users.get(0));
//        System.out.println("role"+ securityUser.getAuthorities());
        return  securityUser;
    }
}
