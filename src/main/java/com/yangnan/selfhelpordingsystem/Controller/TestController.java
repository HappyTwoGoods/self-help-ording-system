package com.yangnan.selfhelpordingsystem.Controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.dao.ManagerDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    ManagerDao managerDao;

    @GetMapping("/test")
    public CommonResult test() {
        return CommonResult.success();
    }
}
