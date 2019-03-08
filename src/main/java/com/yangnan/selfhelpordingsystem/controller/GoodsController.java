package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.GoodsType;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/user/goodsList")
    public CommonResult queryGoods(){
        List<GoodsDTO> goodsDTOList = goodsService.searchGoods(null,null,null);
        if (CollectionUtils.isEmpty(goodsDTOList)){
            return CommonResult.fail(404,"没有相关资源!");
        }
        return CommonResult.success(goodsDTOList);
    }

    @GetMapping("/cook/select/goods")
    public CommonResult queryGoods(Integer cookId) {
        if (cookId == null || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookId(cookId);
        if (CollectionUtils.isEmpty(goodsDTOS)) {
            return CommonResult.fail(404, "找不到数据");
        }
        return CommonResult.success(goodsDTOS);
    }

    @GetMapping("/cook/delete/goods")
    public CommonResult deleteGoods(Integer cookId, Integer goodsId) {
        if (cookId == null || cookId < 1 || goodsId == null || goodsId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        GoodsDTO goodsDTO = goodsService.selectGoodsById(goodsId);
        System.out.println(goodsDTO);
        if (goodsDTO == null || (!goodsDTO.getCookId().equals(cookId))) {
            return CommonResult.fail(403, "菜id与厨师id不匹配");
        }
        int deleteNum = goodsService.deleteGoodsById(goodsId);
        if (deleteNum < 1) {
            return CommonResult.fail(500, "服务器异常");
        }
        return CommonResult.success(200, "删除成功");
    }

    @GetMapping("/cook/updateGoods")
    public CommonResult updateGoods(Integer goodsId, Integer cookId,
                                    @RequestParam(required = false, defaultValue = "") String goodsName,
                                    @RequestParam(required = false, defaultValue = "") Integer type,
                                    @RequestParam(required = false, defaultValue = "") BigDecimal price,
                                    @RequestParam(required = false, defaultValue = "") Integer discount,
                                    @RequestParam(required = false, defaultValue = "") Integer limit,
                                    @RequestParam(required = false, defaultValue = "") String image,
                                    @RequestParam(required = false, defaultValue = "") Integer num,
                                    @RequestParam(required = false, defaultValue = "") String describe) {
        if (goodsId == null || cookId == null || goodsId < 1 || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        GoodsDTO goodsDTO = goodsService.selectGoodsById(goodsId);
        if (goodsDTO == null || !goodsDTO.getCookId().equals(cookId)) {
            return CommonResult.fail(403, "数据不匹配,无法修改");
        }
        GoodsDTO newGoods = new GoodsDTO();
        newGoods.setId(goodsId);
        newGoods.setName(goodsName);
        newGoods.setType(type);
        newGoods.setPrice(price);
        newGoods.setDiscount(discount);
        newGoods.setLimit(limit);
        newGoods.setImage(image);
        newGoods.setGoodsNum(num);
        newGoods.setDescribe(describe);
        int updateNum = goodsService.updateGoodsById(newGoods);
        System.out.println(updateNum);
        if (updateNum < 1) {
            return CommonResult.fail(500, "服务器错误");
        }
        return CommonResult.success();
    }

    @GetMapping("/cook/addGoods")
    public CommonResult addGoods(String goodsName, Integer type, Integer cookId,
                                 BigDecimal price, Integer discount, Integer limit,
                                 String image, Integer num, String describe) {
        if (StringUtils.isEmpty(goodsName) ||
                type == null || type < GoodsType.STAPLE_FOOD || type > GoodsType.BARBECUE ||
                cookId == null || cookId < 1 ||
                price == null || price.compareTo(BigDecimal.valueOf(0)) < 0 ||
                discount == null || discount < 0 || discount > 10 ||
                limit == null || limit < 0 ||
                StringUtils.isEmpty(image) ||
                num == null || num < 0 ||
                StringUtils.isEmpty(describe)) {
            return CommonResult.fail(403, "参数错误");
        }
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setName(goodsName);
        goodsDTO.setType(type);
        goodsDTO.setCookId(cookId);
        goodsDTO.setPrice(price);
        goodsDTO.setDiscount(discount);
        goodsDTO.setLimit(limit);
        goodsDTO.setImage(image);
        goodsDTO.setGoodsNum(num);
        goodsDTO.setDescribe(describe);
        int addNum = goodsService.insertGoods(goodsDTO);
        if (addNum < 1) {
            return CommonResult.fail(500, "服务器异常，新增菜失败");
        }
        return CommonResult.success();
    }
}
