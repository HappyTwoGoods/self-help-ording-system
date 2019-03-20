package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.dto.DeskDTO;
import com.yangnan.selfhelpordingsystem.emun.HttpStatus;
import com.yangnan.selfhelpordingsystem.service.DeskService;
import com.yangnan.selfhelpordingsystem.util.CreateBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DeskInfoController {
    @Resource
    private DeskService deskService;

    @GetMapping("/manager/select/desk")
    public CommonResult selectDesk(@RequestParam(required = false, defaultValue = "") String num) {
        List<DeskDTO> deskDTOS = deskService.queryDeskInfo(num,null);
        if (CollectionUtils.isEmpty(deskDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(deskDTOS);
    }

    @PostMapping("/manager/delete/desk")
    public CommonResult deleteDesk(Integer id) {
        if (id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<DeskDTO> deskDTOS = deskService.queryDeskInfo(null, id);
        if (CollectionUtils.isEmpty(deskDTOS)) {
            return CommonResult.fail(404, "桌号不存在");
        }
        int deleteNum = deskService.deleteById(deskDTOS.get(0).getId());
        if (deleteNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("删除成功");
    }

    @PostMapping("/manager/add/desk")
    public CommonResult addDesk(String num, String describe) {
        if (StringUtils.isEmpty(num) || StringUtils.isEmpty(describe)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        if (!CollectionUtils.isEmpty(deskService.queryDeskInfo(num, null))) {
            return CommonResult.fail(403, "桌号已存在");
        }
        DeskDTO desk = CreateBean.createDesk(num, describe);
        int addNum = deskService.addDeskInfo(desk);
        if (addNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("新增成功！");
    }

    @PostMapping("/manager/update/desk")
    public CommonResult updateDesk(String num, String describe, Integer id) {
        if (StringUtils.isEmpty(num) && StringUtils.isEmpty(describe) || id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<DeskDTO> deskDTOS = deskService.queryDeskInfo(null, id);
        if (CollectionUtils.isEmpty(deskDTOS)) {
            return CommonResult.fail(404, "桌子不存在");
        }
        int updateNum = deskService.updateDeskInfo(num, describe, id);
        if (updateNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("更新成功！");
    }
}
