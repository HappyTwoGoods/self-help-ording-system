package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.BillDTO;

import java.math.BigDecimal;
import java.util.List;

public interface Billservice {
    /**
     * 新增账单
     *
     * @param billDTO
     * @return
     */
    int insertBill(BillDTO billDTO);

    /**
     * 更新账单状态
     * @param id
     * @param status
     * @return
     */
    int updateBillStatus(int id,int status);

    /**
     * 更新商品信息
     * @param id
     * @param goodsInfo
     * @param price
     * @return
     */
    int updateGoodsInfoAndPrice(int id, String goodsInfo, BigDecimal price);

    /**
     * 通过id查订单
     *
     * @param id
     * @return
     */
    BillDTO selectBillById(int id);

    /**
     * 通过用户id查订单
     *
     * @param userId
     * @return
     */
    List<BillDTO> selectBillByUserId(int userId);

    /**
     * 根据订单状态查订单
     * @param status
     * @return
     */
    List<BillDTO> selectBillByStatus(int status);
}
