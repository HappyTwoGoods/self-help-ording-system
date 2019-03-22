package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.GoodsType;
import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.emun.HttpStatus;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import com.yangnan.selfhelpordingsystem.util.CreateBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/user/goodsList")
    public CommonResult queryGoods(@RequestParam(required = false, defaultValue = "") String goodName,
                                   @RequestParam(required = false, defaultValue = "") Integer goodType,
                                   @RequestParam(required = false, defaultValue = "") Integer discount) {
        List<GoodsDTO> goodsDTOList = goodsService.searchGoods(goodName, goodType, discount);
        if (CollectionUtils.isEmpty(goodsDTOList)) {
            return CommonResult.fail(404, "没有相关资源!");
        }
        return CommonResult.success(goodsDTOList);
    }

    @GetMapping("/manager/goodsList")
    public CommonResult selectGoods(@RequestParam(required = false, defaultValue = "") String goodsName,
                                    @RequestParam(required = false, defaultValue = "") Integer goodsType,
                                    @RequestParam(required = false, defaultValue = "") Integer discount) {
        List<GoodsDTO> goodsDTOList = goodsService.searchGoods(goodsName, goodsType, discount);
        if (CollectionUtils.isEmpty(goodsDTOList)) {
            return CommonResult.fail(404, "没有相关资源!");
        }
        return CommonResult.success(goodsDTOList);
    }

    @GetMapping("/cook/select/goods")
    public CommonResult queryGoods(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        if (cookId == null || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookId(cookId);
        if (CollectionUtils.isEmpty(goodsDTOS)) {
            return CommonResult.fail(404, "找不到数据");
        }
        return CommonResult.success(goodsDTOS);
    }

    @GetMapping("/cook/select/goodsById")
    public CommonResult selectGoodsById(Integer goodsId) {
        if (goodsId == null || goodsId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        GoodsDTO goodsDTO = goodsService.selectGoodsById(goodsId);
        if (goodsDTO == null) {
            return CommonResult.fail(404, "找不到资源");
        }
        return CommonResult.success(goodsDTO);
    }

    @PostMapping("/cook/delete/goods")
    public CommonResult deleteGoods(HttpServletRequest request, Integer goodsId) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        if (cookId == null || cookId < 1 || goodsId == null || goodsId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        GoodsDTO goodsDTO = goodsService.selectGoodsById(goodsId);
        if (goodsDTO == null || (!goodsDTO.getCookId().equals(cookId))) {
            return CommonResult.fail(403, "菜id与厨师id不匹配");
        }
        int deleteNum = goodsService.deleteGoodsById(goodsId);
        if (deleteNum < 1) {
            return CommonResult.fail(500, "服务器异常");
        }
        return CommonResult.success(200, "删除成功");
    }

    @PostMapping("/cook/updateGoods")
    public CommonResult updateGoods(Integer goodsId, HttpServletRequest request,
                                    @RequestParam(required = false, defaultValue = "") String goodsName,
                                    @RequestParam(required = false, defaultValue = "") Integer type,
                                    @RequestParam(required = false, defaultValue = "") BigDecimal price,
                                    @RequestParam(required = false, defaultValue = "") Integer discount,
                                    @RequestParam(required = false, defaultValue = "") Integer limit,
                                    @RequestParam(required = false, defaultValue = "") Integer num,
                                    @RequestParam(required = false, defaultValue = "") String describe) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        if (goodsId == null || cookId == null || goodsId < 1 || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        GoodsDTO goodsDTO = goodsService.selectGoodsById(goodsId);
        if (goodsDTO == null || !goodsDTO.getCookId().equals(cookId)) {
            return CommonResult.fail(403, "数据不匹配,无法修改");
        }
        GoodsDTO goods = CreateBean.createGoods(goodsName, type, price, discount, limit, null, num, describe);
        goods.setId(goodsId);
        int updateNum = goodsService.updateGoodsById(goods);
        if (updateNum < 1) {
            return CommonResult.fail(500, "服务器错误");
        }
        return CommonResult.success();
    }

    @PostMapping("/cook/updateGoodsImage")
    public CommonResult updateGoodsImage(MultipartFile photo, Integer goodsId, HttpServletRequest request) {
        if (Objects.isNull(photo) || photo.isEmpty() || goodsId == null || goodsId < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        GoodsDTO goodsDTO = goodsService.selectGoodsById(goodsId);
        if (goodsDTO == null || !goodsDTO.getCookId().equals(cookId)) {
            return CommonResult.fail(403, "数据不匹配,无法修改");
        }
        String image = fileUtil(photo);
        if (image.equals("err")) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        GoodsDTO newGoods = new GoodsDTO();
        newGoods.setId(goodsId);
        newGoods.setImage(image);
        int updateNum = goodsService.updateGoodsById(newGoods);
        if (updateNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("修改成功！");
    }

    @PostMapping("/cook/addGoods")
    public CommonResult addGoods(MultipartFile photo,
                                 String goodsName, Integer type, HttpServletRequest request,
                                 BigDecimal price, Integer discount, Integer limit,
                                 Integer num, String describe) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        if (StringUtils.isEmpty(goodsName) ||
                type == null || type < GoodsType.STAPLE_FOOD || type > GoodsType.BARBECUE ||
                price == null || price.compareTo(BigDecimal.valueOf(0)) < 0 ||
                discount == null || discount < 0 || discount > 10 ||
                limit == null || limit < 0 ||
                Objects.isNull(photo) || photo.isEmpty() ||
                num == null || num < 0 ||
                StringUtils.isEmpty(describe)) {
            return CommonResult.fail(403, "参数错误");
        }
        String image = fileUtil(photo);
        if (image.equals("err")) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        GoodsDTO goods = CreateBean.createGoods(goodsName, type, price, discount, limit, image, num, describe);
        goods.setCookId(cookId);
        int addNum = goodsService.insertGoods(goods);
        if (addNum < 1) {
            return CommonResult.fail(500, "服务器异常，新增菜失败");
        }
        return CommonResult.success(goods);
    }

    private String fileUtil(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return "";
        }
        String photoPath = "D:/MyGraduate/selef-help-manger-side/static/image/";
        String photoPathTwo = "D:/MyGraduate/ording-system-vue/static/image/";
        String imageOne;
        String imageTwo;
        try {
            byte[] bytes = file.getBytes();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
            imageOne = photoPath + sdf.format(date) + file.getOriginalFilename();
            imageTwo = photoPathTwo + sdf.format(date) + file.getOriginalFilename();
            String reImage = "../../static/image/" + sdf.format(date) + file.getOriginalFilename();
            Path pathOne = Paths.get(imageOne);
            if (!Files.isWritable(pathOne)) {
                Files.createDirectories(Paths.get(photoPath));
            }
            Path pathTwo = Paths.get(imageTwo);
            if (!Files.isWritable(pathTwo)) {
                Files.createDirectories(Paths.get(photoPath));
            }
            Files.write(pathOne, bytes);
            Files.write(pathTwo, bytes);
            return reImage;
        } catch (IOException e) {
            e.printStackTrace();
            return "err";
        }
    }
}
