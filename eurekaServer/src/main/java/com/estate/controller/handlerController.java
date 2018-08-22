package com.estate.controller;

import com.estate.model.user.TUser;
import com.estate.service.UserService;
import com.estate.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class handlerController {

    @Value("${securityconfig.urlroles}")
    private String urlroles;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Model model, @RequestParam(value = "userName",required = false)  String userName,
                        @RequestParam(value = "password",required = false) String password){
        if(!StringUtils.isEmpty(userName)){
            Result<TUser> result = userService.queryUserByNameAndPassword(userName,password);
            model.addAttribute("user",result.getData());
        }
      //        Msg msg = new Msg("测试标题", "测试内容", "额外信息，只对管理员显示");
        log.info("login here ============");
        return "home";
    }


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        log.info("login page ============");
        return "login";
    }
    

    @RequestMapping("/getUsers")
    @ResponseBody
    public Result<List<TUser>> getUser(){
        Result<List<TUser>> result = null;
        try {
            result = userService.queryListUser();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("用户不存在{}","asd");
        }
        return result;
    }


    @RequestMapping("/index")
    public String index(ModelMap model, Principal user){
        Authentication authentication = (Authentication)user;
        List<String> userroles = new ArrayList<>();
        for(GrantedAuthority ga: authentication.getAuthorities()){
            userroles.add(ga.getAuthority());
        }
        boolean newrole = false,editrole=false,deleterole = false;
        if(!StringUtils.isEmpty(urlroles)){
            String[] resources= urlroles.split(";");
            for(String resource:resources){
                String[] urls = resource.split("=");
                if(urls[0].indexOf("new")>0){
                    String[] newroles=urls[1].split(",");
                    for(String str:newroles){
                        str = str.trim();
                        if(userroles.contains(str)){
                            newrole=true;
                            break;
                        }
                    }
                }else if(urls[0].indexOf("edit")>0){
                    String[] editroles = urls[1].split(",");
                    for(String str:editroles){
                        str =str.trim();
                        if(userroles.contains(str)){
                            editrole = true;
                            break;
                        }
                    }

                }else if(urls[0].indexOf("delete")>0){
                    String[] deleteroles = urls[1].split(",");
                    for(String str:deleteroles){
                        str =str.trim();
                        if(userroles.contains(str)){
                            deleterole = true;
                            break;
                        }
                    }
                }
            }
        }
        model.addAttribute("newrole",newrole);
        model.addAttribute("editrole",editrole);
        model.addAttribute("deleterole",deleterole);
        model.addAttribute("user",user);
        return "user/index";
    }


//    @Resource
//    private SUserService sUserService;

    @RequestMapping("/home")
    public String home() {
        return "home";

    }


    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String toAdmin(){

        return "helloAdmin";
    }

    @RequestMapping("/hello")
    public String hello() {

        return "hello";

    }
    @RequestMapping("/login?error")
    public String faileLog() {

        return "hello";

    }


    @RequestMapping("/")
    public String root() {
        return "index";

    }

    @RequestMapping("/403")
    public String error(){
        return "403";
    }
}
