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
     * 修改订单状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateDetailStatusById(int id, int status);

    /**
     * 根据商品id和状态查订单
     *
     * @param goodsIds
     * @param status
     * @return
     */
    List<BillDetailDTO> selectOrderByGoodsIds(List<Integer> goodsIds, Integer status);

    /**
     * 根据账单id查订单
     *
     * @param billId
     * @return
     */
    List<BillDetailDTO> selectDetailByBillId(int billId);

    /**
     * 根据状态查详单
     *
     * @param status
     * @return
     */
    List<BillDetailDTO> selectDetailByStatus(int status);

    /**
     * 根据id查订单
     *
     * @param id
     * @return
     */
    BillDetailDTO selectDetailById(int id);

}
