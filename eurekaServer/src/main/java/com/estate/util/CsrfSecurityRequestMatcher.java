package com.estate.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Data
class CsrfSecurityRequestMatcher implements RequestMatcher {
    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    /**
     * 需要排除的url列表
     * @param request
     * @return
     */
    private List<String> execludeUrls;
    @Override
    public boolean matches(HttpServletRequest request) {
        System.out.println("=============123========");
        if(execludeUrls!=null && execludeUrls.size()>0){
            String servletPath = request.getServletPath();
            for (String url: execludeUrls){
                if(servletPath.contains(url)){
                    log.info("++++"+servletPath);
                    return false;
                }
            }
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }
}