package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.BillDetailStatus;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.Billservice;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BillInfoController {
    @Resource
    private Billservice billservice;
    @Resource
    private GoodsService goodsService;
    @Resource
    private BillDetailService billDetailService;

    @GetMapping("/cook/selectNewOrder")
    public CommonResult selectNewOrder(Integer cookId) {
        if (cookId == null || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookId(cookId);
        if (CollectionUtils.isEmpty(goodsDTOS)) {
            return CommonResult.fail(404, "你还没有任何可以做的菜");
        }
        List<Integer> goodsIds = goodsDTOS.stream().map(GoodsDTO::getId).collect(Collectors.toList());
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectOrderByGoodsIds(goodsIds);
        if (CollectionUtils.isEmpty(billDetailDTOS)) {
            return CommonResult.fail(404, "没有下单商品");
        }
        return CommonResult.success(billDetailDTOS);
    }

    @GetMapping("/cook/OrderStatus")
    public CommonResult changeOrderStatus(Integer cookId, Integer orderId) {
        if (cookId == null || cookId < 0 || orderId == null || orderId < 0) {
            return CommonResult.fail(403, "参数错误");
        }
        BillDetailDTO billDetailDTO = billDetailService.selectDetailById(orderId);
        if (billDetailDTO == null) {
            return CommonResult.fail(403, "此订单不存在");
        }
        if (billDetailDTO.getGoodsId() < 0 || billDetailDTO.getStatus() < BillDetailStatus.CONCONFIRM || billDetailDTO.getStatus() >= BillDetailStatus.PRODUCED) {
            return CommonResult.fail(500, "数据有误");
        }
        GoodsDTO goodsDTO = goodsService.selectGoodsById(billDetailDTO.getGoodsId());
        if (goodsDTO == null) {
            return CommonResult.fail(500, "数据有误，查不到此菜");
        }
        if (goodsDTO.getCookId() == null || !goodsDTO.getCookId().equals(cookId)) {
            return CommonResult.fail(403, "这个订单不是与厨师不匹配");
        }
        int updateNum = billDetailService.updateDetailStatusById(orderId, billDetailDTO.getStatus() + 1);
        if (updateNum < 1) {
            return CommonResult.fail(500, "服务器异常");
        }
        return CommonResult.success();
    }

}
