package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.dto.UserAccountDTO;
import com.yangnan.selfhelpordingsystem.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@RestController
public class UserInfoController {

    @Autowired
    private UserAccountService userAccountService;

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/user/login")
    public CommonResult userLogin(String userName, String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (userName == null && password == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        UserAccountDTO userAccountDTO = userAccountService.selectUserAccount(userName, password);
        if (userAccountDTO == null) {
            return CommonResult.fail("登录失败！");
        }
        session.setAttribute(SessionParameters.USERNAME, userName);
        session.setAttribute(SessionParameters.PASSWORD, password);
        session.setAttribute(SessionParameters.USERID, userAccountDTO.getId());
        return CommonResult.success("登录成功！");
    }

    /**
     * 用户注册
     *
     * @param userName
     * @param password
     * @param price
     * @return
     */
    @PostMapping("/user/register")
    public CommonResult userRegister(String userName, String password, BigDecimal price) {
        if (userName == null && password == null && price == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setName(userName);
        userAccountDTO.setPassword(password);
        userAccountDTO.setPrice(price);
        int n = userAccountService.insertUserAccount(userAccountDTO);
        if (n <= 0) {
            return CommonResult.fail("注册失败！");
        }
        return CommonResult.success("注册成功");
    }
}
