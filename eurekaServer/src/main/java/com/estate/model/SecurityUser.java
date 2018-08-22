package com.estate.model;

import com.estate.mapper.role.RoleMapper;
import com.estate.mapper.user.TUserMapper;
import com.estate.model.role.Role;
import com.estate.model.role.RoleExample;
import com.estate.model.user.TUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Slf4j
public class SecurityUser extends TUser implements UserDetails {

    private List<Role> roles;

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    public SecurityUser(TUser tUser) {
        BeanUtils.copyProperties(tUser,this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        RoleExample roleExample = new RoleExample();
//        log.info("user_id"+this.getUserId()+"");
        roleExample.createCriteria().andUserIdEqualTo(this.getUserId());
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if(roles!=null){
            System.out.println("security"+ArrayUtils.toString(roles));
            for (Role role: roles){
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
