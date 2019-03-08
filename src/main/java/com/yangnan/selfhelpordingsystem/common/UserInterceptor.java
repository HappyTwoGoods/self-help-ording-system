package com.yangnan.selfhelpordingsystem.common;

import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.dto.CookDTO;
import com.yangnan.selfhelpordingsystem.dto.UserAccountDTO;
import com.yangnan.selfhelpordingsystem.service.UserAccountService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Resource
    private UserAccountService userAccountService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        try {
            String userName = (String) session.getAttribute(SessionParameters.USERNAME);
            String password = (String) session.getAttribute(SessionParameters.PASSWORD);
            UserAccountDTO userAccountDTO = userAccountService.selectUserAccount(userName, password);
            if (userAccountDTO == null) {
                ManagerInterceptor.sendJson(response);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("用户未登陆");
        }
        ManagerInterceptor.sendJson(response);
        return false;
    }
}
