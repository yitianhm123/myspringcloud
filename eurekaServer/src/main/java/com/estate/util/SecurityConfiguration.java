package com.estate.util;


import com.estate.model.user.TUser;
import com.estate.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecuritySettings securitySettings;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired @Qualifier("dataSource")
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        System.out.println("1231232"+ArrayUtils.toString(securitySettings.getPermitall().split(",")));
        http.formLogin().loginPage("/login").permitAll().successHandler(loginSucessHandle())
        .and().authorizeRequests().antMatchers("/images/**","/Checkcode","/scripts/**","/styles/**","/getUsers","/static/**").permitAll()
        .antMatchers(securitySettings.getPermitall().split(",")).permitAll()
        .anyRequest().authenticated()
        .and().csrf().requireCsrfProtectionMatcher(csrfSecurityRequestMatcher())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and().logout().logoutSuccessUrl(securitySettings.getLogoutsuccessurl())
        .and().exceptionHandling().accessDeniedPage(securitySettings.getDeniedpage())
        .and().rememberMe().tokenValiditySeconds(14*24*60*60);//.tokenRepository(tokenRepository());
    }

    //用户身份验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        System.out.println("1231232"+ArrayUtils.toString(securitySettings.getPermitall().split(",")));
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        //remember me
        auth.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationSuccessHandler loginSucessHandle() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                TUser user =(TUser)authentication.getPrincipal();
                System.out.println("dfasdfasdf"+user.getUserName());
            }
        };
    }

    private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher(){

        CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
        List<String> list = new ArrayList<String>();
        list.add("/rest/");
        csrfSecurityRequestMatcher.setExecludeUrls(list);
        return csrfSecurityRequestMatcher;
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
        jtr.setDataSource(dataSource);
        return jtr;
    }

    @Bean
    public CustomFilterSecurityInterceptor customFilter() throws  Exception{
        CustomFilterSecurityInterceptor customFilter= new CustomFilterSecurityInterceptor();
        customFilter.setSecurityMetadataSource(securityMetadataSource());
        customFilter.setAccessDecisionManager(accessDecisionManager());
        customFilter.setAuthenticationManager(authenticationManager());
        return customFilter;

    }


    @Bean
    public CustomAccessDecisionManager accessDecisionManager(){
        return new CustomAccessDecisionManager();
    }

    @Bean
    public CustomSecurityMetadataSource securityMetadataSource(){
        return new CustomSecurityMetadataSource(securitySettings.getUrlroles());
    }

}


//class loginSucessHandle implements AuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        TUser user =(TUser)authentication.getPrincipal();
//        log.info("登陆用户 user：{} login {}",user.getUserName(), request.getContextPath());
//        log.info("");
//                super.onAuthenticationSuccess(request,response,authentication);
//    }
//
//}