package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.BillStatus;
import com.yangnan.selfhelpordingsystem.dto.BillDTO;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.Billservice;
import com.yangnan.selfhelpordingsystem.util.UserInfoUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class BillInfoController {

    @Resource
    private Billservice billservice;
    @Resource
    private BillDetailService billDetailService;

    @GetMapping("/bill/selectNewBill")
    public CommonResult selectNewBill() {
        List<BillDTO> billDTOS = billservice.selectBillByStatus(BillStatus.PAYED);
        if (CollectionUtils.isEmpty(billDTOS)) {
            return CommonResult.fail(404, "暂时没有数据");
        }
        return CommonResult.success(billDTOS);
    }

    @GetMapping("/bill/getOrder")
    public CommonResult getOrder(Integer goodId, BigDecimal goodPrice,
                                 Integer goodStates, Integer num){
        Integer userId = UserInfoUtil.getUserId();
        BillDTO billDTO = new BillDTO();
        billDTO.setUserId(userId);
        Integer billId = billservice.insertBill(billDTO);
        if (billId <= 0){
            return CommonResult.fail(403,"参数错误！");
        }
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setBillId(billId);
        billDetailDTO.setGoodsId(goodId);
        billDetailDTO.setPrice(goodPrice);
        billDetailDTO.setStatus(goodStates);
        billDetailDTO.setNum(num);
        Integer data = billDetailService.addBillDetail(billDetailDTO);
        if (data <= 0){
            return CommonResult.fail(403,"参数错误！");
        }

        return CommonResult.success();
    }

    @GetMapping("/bill/query/myBill")
    public CommonResult queryBill(Integer billId){
        if (billId <= 0){
            return CommonResult.fail(403,"参数错误！");
        }
        List<BillDetailDTO> billDetailDTOList = billDetailService.selectDetailByBillId(billId);
        if (CollectionUtils.isEmpty(billDetailDTOList)){
            return CommonResult.fail(404,"没有相关资源！");
        }
        return CommonResult.success(billDetailDTOList);
    }

    @GetMapping("/bill/select/states")
    public CommonResult selectByStates(Integer states){
        if (states <= 0){
            return CommonResult.fail(403,"参数错误！");
        }
        List<BillDetailDTO> billDetailDTOList = billDetailService.selectDetailByStatus(states);
        if (CollectionUtils.isEmpty(billDetailDTOList)){
            return CommonResult.fail(404,"没有相关资源");
        }
        return CommonResult.success(billDetailDTOList);
    }



}
