package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.dto.UserAccountDTO;
import com.yangnan.selfhelpordingsystem.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        if (userAccountDTO.getId() == null) {
            return CommonResult.fail(404,"登录失败！");
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
        if (userName == null && password == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setName(userName);
        userAccountDTO.setPassword(password);
        userAccountDTO.setPrice(price);
        int n = userAccountService.insertUserAccount(userAccountDTO);
        if (n <= 0) {
            return CommonResult.fail(500,"注册失败！");
        }
        return CommonResult.success("注册成功");
    }

    /**
     * 账户充值
     *
     * @param addPrice
     * @param password
     * @param request
     * @return
     */
    @GetMapping("/user/add/price")
    public CommonResult addPrice(BigDecimal addPrice,String password, HttpServletRequest request){
        if (password == null){
            return CommonResult.fail(403,"参数错误！");
        }
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        UserAccountDTO userAccountDTO = userAccountService.queryBuId(userId);
        BigDecimal price = userAccountDTO.getPrice();
        String myPassword = userAccountDTO.getPassword();
        if (!myPassword.equals(password)){
            return CommonResult.fail(403,"密码不正确！");
        }
        int data = userAccountService.updatePrice(price.add(addPrice),userId,password);
        if (data <= 0){
            return CommonResult.fail(500,"充值失败!");
        }
        return CommonResult.success();
    }

    @GetMapping("/user/query/id")
    public CommonResult queryById(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        if (userId == null || userId <= 0){
            return CommonResult.fail(403,"参数错误！");
        }
        UserAccountDTO userAccountDTO = userAccountService.queryBuId(userId);
        if (userAccountDTO == null){
            return CommonResult.fail(404,"没有该用户信息");
        }
        return CommonResult.success(userAccountDTO);
    }
}
