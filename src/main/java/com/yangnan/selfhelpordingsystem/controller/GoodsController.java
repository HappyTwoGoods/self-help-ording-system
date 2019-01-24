package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.service.Billservice;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class GoodsController {
    @Resource
    GoodsService goodsService;
    @Resource
    Billservice billservice;

    @GetMapping("goods/revise")
    public CommonResult reviseGoods(GoodsDTO goodsDTO) {
        if (goodsDTO == null) {
            return CommonResult.fail(403, "参数有误");
        }
        if (goodsDTO.getId() == null || goodsDTO.getId() < 1) {
            return CommonResult.fail(403, "请重新选择您要修改的菜");
        }
        GoodsDTO goods = goodsService.selectGoodsById(goodsDTO.getId());
        if (goods == null) {
            return CommonResult.fail(403, "此菜不存在");
        }
        int result = goodsService.updateGoodsById(goodsDTO);
        if (result < 1) {
            return CommonResult.fail(500, "服务器异常");
        }
        return CommonResult.success();
    }
}
