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
     * 用户根据状态查看订单
     *
     * @param state
     * @param billId
     * @return
     */
    List<BillDetailDTO> selectUserDetailByState(int state, int billId);

    /**
     * 根据id查订单
     *
     * @param id
     * @return
     */
    BillDetailDTO selectDetailById(int id);

    /**
     * 根据bilId查询订单详情
     *
     * @param billId
     * @return
     */
    List<BillDetailDTO> queryByBillId(int billId);

}
