package com.estate.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;


@Slf4j
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
         if(collection == null){
             return ;
         }
         // config urlroles
         Iterator<ConfigAttribute> iterator = collection.iterator();
         while(iterator.hasNext()){
             ConfigAttribute configAttribute =iterator.next();
             //need role
             String needRole = configAttribute.getAttribute();
             //user roles
             for(GrantedAuthority ga:authentication.getAuthorities()){
                    if(needRole.equals(ga.getAuthority())){
                        return;
                 }
             }
             log.info("need is role {}",needRole);
         }
         throw new AccessDeniedException("cann't Access");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
