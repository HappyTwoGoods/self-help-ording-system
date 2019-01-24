package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;

import java.util.List;

public interface BillDetailService {
    /**
     * 新增订单
     *
     * @param detail
     * @return
     */
    int addBillDetail(BillDetailDTO detail);

    /**
     * 修改订单
     *
     * @param id
     * @param status
     * @return
     */
    int updateDetailStatusById(int id, int status);

    /**
     * 根据商品id查询订单
     *
     * @param goodsIds
     * @return
     */
    List<BillDetailDTO> selectDetailByGoodsIds(List<Integer> goodsIds);

    /**
     * 根据账单id查订单
     *
     * @param billId
     * @return
     */
    List<BillDetailDTO> selectDetailByBillId(int billId);
}
