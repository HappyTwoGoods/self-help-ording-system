package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.BillDetailStatus;
import com.yangnan.selfhelpordingsystem.constant.CookStatus;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import com.yangnan.selfhelpordingsystem.dto.CookDTO;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.CookService;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CookInfoController {

    @Autowired
    private CookService cookService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private BillDetailService billDetailService;

    /**
     * 动态查询厨师信息
     *
     * @param name
     * @param telephone
     * @param status
     * @return
     */
    @GetMapping("/manager/query/CookInfo")
    public CommonResult selectCookInfo(@RequestParam(required = false, defaultValue = "") String name,
                                       @RequestParam(required = false, defaultValue = "") String telephone,
                                       @RequestParam(required = false, defaultValue = "") Integer status) {
        List<CookDTO> cookDTOList = cookService.queryCookInfo(name, telephone, status);
        System.out.println(cookDTOList);
        if (CollectionUtils.isEmpty(cookDTOList)) {
            return CommonResult.fail(404, "暂无相关信息");
        }
        return CommonResult.success(cookDTOList);
    }

    /**
     * 动态修改厨师信息
     *
     * @param cookName
     * @param telephone
     * @param cookId
     * @return
     */
    @GetMapping("/update/cookInfo")
    public CommonResult updateCookInfo(Integer cookId,
                                       @RequestParam(required = false, defaultValue = "") String cookName,
                                       @RequestParam(required = false, defaultValue = "") String telephone,
                                       @RequestParam(required = false, defaultValue = "") String nickname,
                                       @RequestParam(required = false, defaultValue = "") String cookPassword) {
        if (cookId == null) {
            return CommonResult.fail(403, "参数错误");
        }
        CookDTO cookDTO = new CookDTO();
        cookDTO.setCookName(cookName);
        cookDTO.setTelephone(telephone);
        cookDTO.setNickname(nickname);
        cookDTO.setCookPassword(cookPassword);
        cookDTO.setId(cookId);
        int result = cookService.updateCookInfo(cookDTO);
        if (result <= 0) {
            return CommonResult.fail("修改信息失败");
        }
        return CommonResult.success("修改成功");
    }

    /**
     * 添加厨师信息
     *
     * @param cookName
     * @param telephone
     * @param nickname
     * @param password
     * @return
     */
    @GetMapping("/manager/add/cookInfo")
    public CommonResult addCookInfo(String cookName,
                                    String telephone,
                                    String nickname,
                                    String password) {
        if (StringUtils.isEmpty(cookName) || StringUtils.isEmpty(telephone) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            return CommonResult.fail(403, "参数错误");
        }
        CookDTO cookDTO = new CookDTO();
        cookDTO.setCookName(cookName);
        cookDTO.setTelephone(telephone);
        cookDTO.setNickname(nickname);
        cookDTO.setCookPassword(password);
        int result = cookService.addCookerInfo(cookDTO);
        if (result <= 0) {
            return CommonResult.fail("添加失败");
        }
        return CommonResult.success("添加成功");
    }

    /**
     * 根据id删除厨师信息
     *
     * @param cookId
     * @return
     */
    @GetMapping("/cook/delete/info")
    public CommonResult deleteCookInfo(Integer cookId) {
        if (cookId == null) {
            return CommonResult.fail(403, "参数错误");
        }
        int result = cookService.deleteCookerInfo(cookId);
        if (result <= 0) {
            return CommonResult.fail("删除失败");
        }
        return CommonResult.success("删除成功");
    }

    @GetMapping("/cook/login")
    public CommonResult login(String username, String password) {
        if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
            return CommonResult.fail(403, "参数错误");
        }
        CookDTO cookDTO = cookService.selectCook(username, password);
        if (cookDTO == null) {
            return CommonResult.fail(404, "查不到对应用户");
        }
        cookService.updateStatusById(cookDTO.getId(), CookStatus.WORK);
        return CommonResult.success();
    }

    @GetMapping("/cook/loginOut")
    public CommonResult loginOut(Integer cookId) {
        if (cookId == null || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookId(cookId);
        if (!CollectionUtils.isEmpty(goodsDTOS)) {
            List<Integer> goodsIds = goodsDTOS.stream().map(GoodsDTO::getId).collect(Collectors.toList());
            List<BillDetailDTO> billDetailDTOS = billDetailService.selectOrderByGoodsIds(goodsIds, BillDetailStatus.PRODUCING);
            if (!CollectionUtils.isEmpty(billDetailDTOS)) {
                return CommonResult.fail(403, "你还有正在做的菜，无法休息，参数错误");
            }
        }
        cookService.updateStatusById(cookId, CookStatus.REST);
        return CommonResult.success();
    }
}
