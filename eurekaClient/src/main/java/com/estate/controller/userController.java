package com.estate.controller;

import com.estate.model.user.TUser;
import com.estate.service.UserService;
import com.estate.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class userController {



    @Autowired
    private UserService userService;


    @RequestMapping("/hi")
    public String replyHi(){
        return "hello";
    }



}
