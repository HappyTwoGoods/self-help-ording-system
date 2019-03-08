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
     * 根据商品id和状态动态查订单
     *
     * @param goodsIds
     * @param status
     * @return
     */
    List<BillDetailEntity> selectOrderByGoodsIds(@Param("goodsIds") List<Integer> goodsIds, @Param("status") Integer status);

    /**
     * 根据账单id查订单
     *
     * @param billId
     * @return
     */
    List<BillDetailEntity> selectDetailByBillId(@Param("billId") int billId);

    /**
     * 根据状态查订单
     *
     * @param status
     * @return
     */
    List<BillDetailEntity> selectDetailByStatus(@Param("status") int status);

    /**
     * 根据id查订单
     *
     * @param id
     * @return
     */
    BillDetailEntity selectDetailById(@Param("id") int id);

    /**
     * 根据billId查找订单信息
     *
     * @param billId
     * @return
     */
    List<BillDetailEntity> queryByBillId(@Param("billId") int billId);
}
