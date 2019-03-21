package com.yangnan.selfhelpordingsystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangnan.selfhelpordingsystem.common.CommonResult;
import com.yangnan.selfhelpordingsystem.constant.BillDetailStatus;
import com.yangnan.selfhelpordingsystem.constant.BillStatus;
import com.yangnan.selfhelpordingsystem.constant.SessionParameters;
import com.yangnan.selfhelpordingsystem.dto.BillDTO;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.dto.UserAccountDTO;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.service.Billservice;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import com.yangnan.selfhelpordingsystem.service.UserAccountService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.color.ICC_Profile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private UserAccountService userAccountService;

    @GetMapping("/cook/selectOrderByState")
    public CommonResult selectNewOrder(HttpServletRequest request,
                                       @RequestParam(required = false, defaultValue = "") Integer state) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
        if (cookId == null || cookId < 1) {
            return CommonResult.fail(403, "参数错误");
        }
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookId(cookId);
        if (CollectionUtils.isEmpty(goodsDTOS)) {
            return CommonResult.fail(404, "你还没有任何可以做的菜");
        }
        List<Integer> goodsIds = goodsDTOS.stream().map(GoodsDTO::getId).collect(Collectors.toList());
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectOrderByGoodsIds(goodsIds, state);
        if (CollectionUtils.isEmpty(billDetailDTOS)) {
            return CommonResult.fail(404, "找不到数据");
        }

        return CommonResult.success(createBillDetail(billDetailDTOS));
    }

    @GetMapping("/manager/select/billDetail")
    public CommonResult selectBillDetail(@RequestParam(required = false,defaultValue = "") Integer status) {
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectDetailByStatus(status);
        if (CollectionUtils.isEmpty(billDetailDTOS)) {
            return CommonResult.fail(404, "找不到资源");
        }
        return CommonResult.success(createBillDetail(billDetailDTOS));
    }

    @GetMapping("/cook/changeOrderStatus")
    public CommonResult changeOrderStatus(Integer orderId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cookId = (Integer) session.getAttribute(SessionParameters.COOKID);
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

    /**
     * 用户下单
     *
     * @param billDetail
     * @return
     */
    @GetMapping("/user/getOrder")
    public CommonResult getOrder(HttpServletRequest request, String billDetail) {
        System.out.println(billDetail);
        if (billDetail == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        JSONObject jsonObject = JSON.parseObject(billDetail);
        Integer goodsId = jsonObject.getInteger("goodsId");
        Integer num = jsonObject.getInteger("num");
        BigDecimal price = jsonObject.getBigDecimal("price");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        BillDTO billDTO = billservice.queryBillId(userId,BillStatus.CONFIRM);
        if (billDTO == null){
            BillDTO newBillDTO = new BillDTO();
            newBillDTO.setPrice(price.multiply((BigDecimal.valueOf(num))));
            newBillDTO.setStatus(BillStatus.CONFIRM);
            newBillDTO.setUserId(userId);
            newBillDTO.setPayType(0);
            int billId = billservice.insertBill(newBillDTO);
            if (billId <= 0){
                return CommonResult.fail(500,"添加订单失败！");
            }
            BillDetailDTO billDetailDTO = new BillDetailDTO();
            billDetailDTO.setGoodsId(goodsId);
            billDetailDTO.setNum(num);
            billDetailDTO.setPrice(price.multiply(BigDecimal.valueOf(num)));
            billDetailDTO.setBillId(billId);
            billDetailDTO.setStatus(BillDetailStatus.CONCONFIRM);
            int result = billDetailService.addBillDetail(billDetailDTO);
            if (result <= 0) {
                return CommonResult.fail(500,"添加菜品失败！");
            }
            return CommonResult.success();
        }
        BigDecimal addPrice = billDTO.getPrice();
        BillDTO billDTOAddPrice = billservice.updatePrice(addPrice.add(price),billDTO.getId(),billDTO.getStatus());
        if (billDTOAddPrice == null) {
            return CommonResult.fail(500,"更换订单信息失败！");
        }
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setStatus(BillDetailStatus.CONCONFIRM);
        billDetailDTO.setBillId(billDTO.getId());
        billDetailDTO.setPrice(price.multiply(BigDecimal.valueOf(num)));
        billDetailDTO.setNum(num);
        billDetailDTO.setGoodsId(goodsId);
        int result = billDetailService.addBillDetail(billDetailDTO);
        if (result <= 0) {
            return CommonResult.fail(500,"添加菜品失败！");
        }
        return CommonResult.success();
    }

//    /**
//     * 继续添加菜品
//     *
//     * @param billDetails
//     * @return
//     */
//    @GetMapping("/user/add/goods")
//    public CommonResult addGoods(HttpServletRequest request, BillDetail... billDetails) {
//        HttpSession session = request.getSession();
//        if (billDetails.length <= 0) {
//            return CommonResult.fail(403, "参数错误！");
//        }
//        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
//        int billId = billservice.queryBillId(userId, BillStatus.CONFIRM);
//        if (billId <= 0) {
//            return CommonResult.fail(404, "没有相应的订单号！");
//        }
//        BigDecimal priceSum = new BigDecimal(0.00);
//        for (BillDetail billDetail : billDetails) {
//            BigDecimal price = billDetail.getPrice();
//            int mun = billDetail.getNum();
//            priceSum = priceSum.add(price.multiply(BigDecimal.valueOf(mun)));
//        }
//        billservice.updatePrice(priceSum, billId, BillStatus.CONFIRM);
//        BillDetailDTO billDetailDTO = new BillDetailDTO();
//        for (BillDetail billDetail : billDetails) {
//            billDetailDTO.setBillId(billId);
//            billDetailDTO.setGoodsId(billDetail.getGoodsId());
//            billDetailDTO.setPrice(billDetail.getPrice());
//            billDetailDTO.setNum(billDetail.getNum());
//            billDetailDTO.setStatus(BillDetailStatus.CONCONFIRM);
//            int data = billDetailService.addBillDetail(billDetailDTO);
//            if (data <= 0) {
//                return CommonResult.fail(500, "添加菜品失败！");
//            }
//        }
//
//        return CommonResult.success();
//    }

    /**
     * 查看用户订单详情
     *
     * @return
     */
    @GetMapping("/user/query/myBill")
    public CommonResult queryBill(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        BillDTO billDTO = billservice.queryBillId(userId, BillStatus.CONFIRM);
        if (billDTO == null) {
            return CommonResult.fail(404, "没有相应的订单号！");
        }
        List<BillDetailDTO> billDetailDTOList = billDetailService.selectDetailByBillId(billDTO.getId());
        return CommonResult.success(billDetailDTOList);
    }

    /**
     * 根据状态查找已下单菜品
     *
     * @param request
     * @param states
     * @return
     */
    @GetMapping("/user/select/states")
    public CommonResult selectByStates(HttpServletRequest request, Integer states) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        BillDTO billDTO  = billservice.queryBillId(userId,BillStatus.CONFIRM);
        if (states <= 0) {
            return CommonResult.fail(403, "参数错误！");
        }
        List<BillDetailDTO> billDetailDTOList = billDetailService.selectUserDetailByState(states,billDTO.getId());
        if (CollectionUtils.isEmpty(billDetailDTOList)) {
            return CommonResult.fail(404, "没有相关资源");
        }
        return CommonResult.success(billDetailDTOList);
    }

    /**
     * 结账
     *
     * @param userPassword
     * @return
     */
    @GetMapping("/user/settle/accounts")
    public CommonResult settleAccounts(String userPassword, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        BillDTO billDTO = billservice.queryBillId(userId, BillStatus.CONFIRM);
        if (billDTO == null) {
            return CommonResult.success();
        }
        BillDTO billDTOs = billservice.selectBillById(billDTO.getId());
        if (billDTOs == null) {
            return CommonResult.fail(404, "没有相应订单信息！");
        }
        BigDecimal price = billDTOs.getPrice();
        UserAccountDTO userAccountDTO = userAccountService.queryBuId(userId);
        BigDecimal userPrice = userAccountDTO.getPrice();
        String myPassword = userAccountDTO.getPassword();
        if (!myPassword.equals(userPassword)){
            return CommonResult.fail(403,"密码错误！");
        }
        int data = userAccountService.updatePrice(userPrice.subtract(price), userId, userPassword);
        if (data <= 0) {
            return CommonResult.fail(500, "扣除用户余额失败！");
        }
        billservice.updatePrice(BigDecimal.ZERO, billDTO.getId(), BillStatus.PAYED);
        return CommonResult.success();
    }

    /**
     * 取消订单
     *
     * @param billDetailId
     * @return
     */
    @GetMapping("/user/cancel/order")
    public CommonResult cancelOrder(int billDetailId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        if (billDetailId <= 0) {
            return CommonResult.fail(403, "参数错误!");
        }
        int data = billDetailService.updateDetailStatusById(billDetailId, BillDetailStatus.CANCEL);
        if (data <= 0) {
            return CommonResult.fail(500, "取消订单失败！");
        }
        BillDTO billDTOs = billservice.queryBillId(userId,BillStatus.CONFIRM);
        BillDTO billDTO = billservice.selectBillById(billDTOs.getId());
        BigDecimal billPrice = billDTO.getPrice();
        BillDetailDTO billDetailDTO = billDetailService.selectDetailById(billDetailId);
        BigDecimal billDetailPrice = billDetailDTO.getPrice();
        BigDecimal price = billPrice.subtract(billDetailPrice);
        billservice.updatePrice(price,billDTOs.getId(),BillStatus.CANCEL);
        return CommonResult.success();
    }

    /**
     * 详情实体类
     */
    @Data
    private class BillDetail {

        private Integer goodsId;

        private String goodsName;

        private BigDecimal price;

        private Integer num;
    }

    private List<WebBillDetail> createBillDetail(List<BillDetailDTO> billDetailDTOS) {
        if (CollectionUtils.isEmpty(billDetailDTOS)) {
            return new ArrayList<>();
        }
        ArrayList<WebBillDetail> webBillDetails = new ArrayList<>();
        for (BillDetailDTO billDetail : billDetailDTOS) {
            WebBillDetail webBillDetail = new WebBillDetail();
            BeanUtils.copyProperties(billDetail, webBillDetail);
            webBillDetail.setGoodsName(goodsService.selectGoodsById(billDetail.getGoodsId()).getName());
            webBillDetails.add(webBillDetail);
        }
        return webBillDetails;
    }

    @Data
    private class WebBillDetail {
        private Integer id;
        private String goodsName;
        private Integer num;
        private Integer status;
        private Date updateTime;
    }

}
