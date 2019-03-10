package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.BillDetailStatus;
import com.yangnan.selfhelpordingsystem.constant.CookStatus;
import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import com.yangnan.selfhelpordingsystem.dto.CookDTO;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.CookService;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import org.apache.logging.log4j.util.Strings;
import org.checkerframework.checker.propkey.qual.PropertyKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        if (CollectionUtils.isEmpty(cookDTOList)) {
            return CommonResult.fail(404, "暂无相关信息");
        }
        return CommonResult.success(cookDTOList);
    }

    /**
     * 动态修改厨师信息
     *
     * @param request
     * @param cookName
     * @param telephone
     * @return
     */
    @PostMapping ("/cook/update/cookInfo")
    public CommonResult updateCookInfo(HttpServletRequest request,
                                       @RequestParam(required = false, defaultValue = "") String cookName,
                                       @RequestParam(required = false, defaultValue = "") String telephone,
                                       @RequestParam(required = false, defaultValue = "") String nickname,
                                       @RequestParam(required = false, defaultValue = "") String cookPassword) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
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
    @GetMapping("/manager/delete/cookInfo")
    public CommonResult deleteCookInfo(Integer cookId) {
        if (cookId == null) {
            return CommonResult.fail(403, "参数错误");
        }
        if (selectCookOrder(cookId)) {
            return CommonResult.fail(403, "暂时无法删除厨师，正在做菜");
        }
        int result = cookService.deleteCookerInfo(cookId);
        if (result <= 0) {
            return CommonResult.fail("删除失败");
        }
        return CommonResult.success("删除成功");
    }

    @GetMapping("/cook/selectCookById")
    public CommonResult selectCookById(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        CookDTO cookDTO = cookService.selectCookById(cookId);
        if (cookDTO == null) {
            return CommonResult.fail(404, "找不到资源");
        }
        return CommonResult.success(cookDTO);
    }

    @PostMapping("cook/login")
    public CommonResult login(String username, String password, HttpServletRequest request) {
        if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
            return CommonResult.fail(403, "参数错误");
        }
        CookDTO cookDTO = cookService.selectCook(username, password);
        if (cookDTO == null) {
            return CommonResult.fail(404, "查不到对应用户");
        }
        try {
            HttpSession session = request.getSession();
            session.setAttribute(SessionParameters.USERNAME, username);
            session.setAttribute(SessionParameters.PASSWORD, password);
            session.setAttribute(SessionParameters.COOKID, cookDTO.getId());
            cookService.updateStatusById(cookDTO.getId(), CookStatus.WORK);
            return CommonResult.success().setMessage("登录成功");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        return CommonResult.fail(500, "服务器异常");
    }

    @GetMapping("/cook/loginOut")
    public CommonResult loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        if (cookId == null || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        if (selectCookOrder(cookId)) {
            return CommonResult.fail(403, "参数错误,无法休息，有正在做的菜");
        }
        try {
            session.setAttribute(SessionParameters.USERNAME, "");
            session.setAttribute(SessionParameters.PASSWORD, "");
            session.setAttribute(SessionParameters.COOKID, 0);
            cookService.updateStatusById(cookId, CookStatus.REST);
            return CommonResult.success(200, "退出登录成功");
        } catch (Exception e) {
            System.out.println("退出登录失败");
        }
        return CommonResult.fail(500, "服务器异常");
    }

    private Boolean selectCookOrder(Integer cookId) {
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookId(cookId);
        if (!CollectionUtils.isEmpty(goodsDTOS)) {
            List<Integer> goodsIds = goodsDTOS.stream().map(GoodsDTO::getId).collect(Collectors.toList());
            List<BillDetailDTO> billDetailDTOS = billDetailService.selectOrderByGoodsIds(goodsIds, BillDetailStatus.PRODUCING);
            if (!CollectionUtils.isEmpty(billDetailDTOS)) {
                return true;
            }
        }
        return false;
    }
}
