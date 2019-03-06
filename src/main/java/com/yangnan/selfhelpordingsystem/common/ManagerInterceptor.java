package com.yangnan.selfhelpordingsystem.common;

import com.alibaba.fastjson.JSON;
import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.service.ManagerService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ManagerInterceptor implements HandlerInterceptor {
    @Resource
    private ManagerService managerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        try {
            String userName = (String) session.getAttribute(SessionParameters.USERNAME);
            String password = (String) session.getAttribute(SessionParameters.PASSWORD);
            int managerId = managerService.selectManager(userName, password);
            if (managerId < 1) {
                sendJson(response);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("用户未登陆");
        }
        sendJson(response);
        return false;
    }

    static void sendJson(HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(CommonResult.fail(401, "没有登录！")));
        writer.close();
        response.flushBuffer();
    }
}
