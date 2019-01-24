package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.BillStatus;
import com.yangnan.selfhelpordingsystem.dto.BillDTO;
import com.yangnan.selfhelpordingsystem.service.Billservice;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BillInfoController {
    @Resource
    Billservice billservice;

    @GetMapping("/bill/selectNewBill")
    public CommonResult selectNewBill() {
        List<BillDTO> billDTOS = billservice.selectBillByStatus(BillStatus.PAYED);
        if (CollectionUtils.isEmpty(billDTOS)) {
            return CommonResult.fail(404, "暂时没有数据");
        }
        return CommonResult.success(billDTOS);
    }

}
