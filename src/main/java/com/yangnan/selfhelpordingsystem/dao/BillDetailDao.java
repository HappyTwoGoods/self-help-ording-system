package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.entity.BillDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillDetailDao {
    /**
     * 新增订单
     *
     * @param detail
     * @return
     */
    int addBillDetail(@Param("detail") BillDetailEntity detail);

    /**
     * 修改订单
     *
     * @param id
     * @param status
     * @return
     */
    int updateDetailStatusById(@Param("id") int id,
                               @Param("status") int status);

    /**
     * 根据商品id查询订单
     *
     * @param goodsIds
     * @return
     */
    List<BillDetailEntity> selectDetailByGoodsIds(@Param("goodsIds") List<Integer> goodsIds);

    /**
     * 根据账单id查订单
     *
     * @param billId
     * @return
     */
    List<BillDetailEntity> selectDetailByBillId(@Param("billId") int billId);

}