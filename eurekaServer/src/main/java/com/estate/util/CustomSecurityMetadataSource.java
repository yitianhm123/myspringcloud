package com.estate.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;


@Slf4j
@Data
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<String,Collection<ConfigAttribute>> resourceMap =null;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private String urlroles;


    public CustomSecurityMetadataSource(String urlroles){
        super();
        this.urlroles = urlroles;
        resourceMap = loadResourceMatchAuthority();
    }

    private Map<String,Collection<ConfigAttribute>> loadResourceMatchAuthority() {
        Map<String,Collection<ConfigAttribute>> map = new HashMap<String,Collection<ConfigAttribute>>();
        if(urlroles !=null && !urlroles.isEmpty()){
            String[] resources = urlroles.split(";");
            for(String resource: resources){
                String[] urls= resource.split("=");
                String[] roles = urls[1].split(",");
                Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
                for(String role:roles){
                    ConfigAttribute config = new SecurityConfig(role.trim());
                    list.add(config);
                }
                map.put(urls[0].trim(),list);
            }
        }else{
                log.error("securityconfig.uslroles must be set");
        }

        log.info("load urlroles Resource");
        return  map;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String url=((FilterInvocation)o).getRequestUrl();
        log.debug("requesturl is {}",url);
        if(resourceMap ==null){
            resourceMap = loadResourceMatchAuthority();
        }
        Iterator<String> iterator = resourceMap.keySet().iterator();
        while(iterator.hasNext()){
          String reUrl = iterator.next();
          if(pathMatcher.match(reUrl,url)){
               return resourceMap.get(reUrl);
          }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
