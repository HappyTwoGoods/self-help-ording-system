package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.CookStatus;
import com.yangnan.selfhelpordingsystem.dto.CookDTO;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.CookService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CookInfoController {

    @Autowired
    private CookService cookService;
    @Resource
    private BillDetailService billDetailService;

    /**
     * 动态查询厨师信息
     *
     * @param cookName
     * @param telephone
     * @param cookStatus
     * @return
     */
    @GetMapping("/cook/query/info")
    public CommonResult selectCookInfo(@RequestParam(required = false, defaultValue = "") String cookName,
                                       @RequestParam(required = false, defaultValue = "") String telephone,
                                       @RequestParam(required = false, defaultValue = "-1") Integer cookStatus) {
        List<CookDTO> cookDTOList = cookService.queryCookInfo(cookName, telephone, cookStatus);
        if (cookDTOList.isEmpty()) {
            return CommonResult.fail(404, "暂无相关信息");
        }
        return CommonResult.success(cookDTOList);
    }

    /**
     * 动态修改厨师信息
     *
     * @param cookName
     * @param telephone
     * @param cookStatus
     * @param cookId
     * @return
     */
    @GetMapping("/cook/update/info")
    public CommonResult updateCookInfo(@RequestParam(required = false, defaultValue = "") String cookName,
                                       @RequestParam(required = false, defaultValue = "") String telephone,
                                       @RequestParam(required = false, defaultValue = "-1") Integer cookStatus,
                                       @RequestParam(required = false, defaultValue = "") String nickname,
                                       @RequestParam(required = false, defaultValue = "") String cookPassword,
                                       @RequestParam(required = true, defaultValue = "0") Integer cookId) {
        if (cookId == null) {
            return CommonResult.fail(403, "参数错误");
        }
        CookDTO cookDTO = new CookDTO();
        cookDTO.setCookName(cookName);
        cookDTO.setTelephone(telephone);
        cookDTO.setCookStatus(cookStatus);
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
     * @param cookStatus
     * @return
     */
    @GetMapping("/cook/add/info")
    public CommonResult addCookInfo(String cookName,
                                    String telephone,
                                    Integer cookStatus) {
        if (cookName == null && telephone == null && cookStatus < 0) {
            return CommonResult.fail(403, "参数错误");
        }
        CookDTO cookDTO = new CookDTO();
        cookDTO.setCookName(cookName);
        cookDTO.setTelephone(telephone);
        cookDTO.setCookStatus(cookStatus);
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
        int id = cookService.selectCook(username, password);
        if (id < 1) {
            return CommonResult.fail(404, "查不到对应用户");
        }
        cookService.updateStatusById(id, CookStatus.WORK);
        return CommonResult.success();
    }

    @GetMapping("/cook/loginOut")
    public CommonResult loginOut(Integer cookId) {
        if(cookId==null||cookId<1){
           return CommonResult.fail(403,"参数错误");
        }
        cookService.updateStatusById(cookId, CookStatus.REST);
        return CommonResult.success();
    }
}
