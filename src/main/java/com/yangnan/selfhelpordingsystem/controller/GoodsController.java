package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 修改商品信息
     *
     * @param goodsDTO
     * @return
     */
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

    /**
     * 动态查询商品信息
     *
     * @param goodsName
     * @param goodType
     * @param discount
     * @return
     */
    @GetMapping("/goods/query")
    public CommonResult selectGoods(@RequestParam(required = false, defaultValue = "")String goodsName,
                                    @RequestParam(required = false, defaultValue = "")Integer goodType,
                                    @RequestParam(required = false, defaultValue = "")Integer discount){
        List<GoodsDTO> goodsDTOList = goodsService.searchGoods(goodsName,goodType,discount);
        if (goodsDTOList.isEmpty()){
            return CommonResult.fail(404,"没有相关资源！");
        }
        return CommonResult.success(goodsDTOList);
    }

}
