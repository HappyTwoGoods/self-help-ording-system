package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.BillDetailStatus;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.ManagerService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ManagerInfoController {
    @Resource
    private ManagerService managerService;
    @Resource
    private BillDetailService billDetailService;

    @GetMapping("/manager/login")
    public CommonResult managerLogin(String name, String password) {
        if(Strings.isEmpty(name)||Strings.isEmpty(password)){
            return CommonResult.fail(403,"参数错误");
        }
        int managerNum = managerService.selectManager(name, password);
        if (managerNum < 1) {
            return CommonResult.fail(404, "查不到对应用户");
        }
        return CommonResult.success();
    }

    @GetMapping("/select/billDetail")
    public CommonResult selectBillDetail(Integer status) {
        if(status==null||status < BillDetailStatus.CANCEL || status > BillDetailStatus.PRODUCED){
            return CommonResult.fail(403,"参数错误");
        }
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectDetailByStatus(status);
        if (CollectionUtils.isEmpty(billDetailDTOS)) {
            return CommonResult.fail(404, "找不到资源");
        }
        return CommonResult.success(billDetailDTOS);
    }



}
