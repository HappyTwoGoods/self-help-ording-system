package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.ManagerService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ManagerInfoController {
    @Resource
    private ManagerService managerService;
    @Resource
    private BillDetailService billDetailService;

    @PostMapping("/manager/login")
    public CommonResult managerLogin(String username, String password, HttpServletRequest request) {
        if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
            return CommonResult.fail(403, "参数错误");
        }
        int managerId = managerService.selectManager(username, password);
        if (managerId < 1) {
            return CommonResult.fail(404, "查不到对应用户");
        }
        try {
            HttpSession session = request.getSession();
            session.setAttribute(SessionParameters.USERNAME, username);
            session.setAttribute(SessionParameters.PASSWORD, password);
            session.setAttribute(SessionParameters.MANAGERID, managerId);
            return CommonResult.success().setMessage("登录成功");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        return CommonResult.fail(500, "服务器异常");
    }


}
