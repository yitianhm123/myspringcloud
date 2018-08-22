package com.estate.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "securityconfig")
public  class SecuritySettings {
    private  String logoutsuccessurl = "/logout";
    private  String permitall = "/api";
    private  String deniedpage = "/deny" ;
    private  String urlroles;

}
