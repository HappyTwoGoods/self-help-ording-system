package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.dto.BillDTO;
import com.yangnan.selfhelpordingsystem.entity.BillEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BillDao {
    /**
     * 新增账单
     *
     * @param billEntity
     * @return
     */
    int insertBill(@Param("bill") BillEntity billEntity);

    /**
     * 更新账单状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateBillStatus(@Param("id") int id,
                         @Param("status") int status);

    /**
     * 通过id查订单
     *
     * @param id
     * @return
     */
    BillEntity selectBillById(@Param("id") int id);

    /**
     * 通过用户id查订单
     *
     * @param userId
     * @return
     */
    List<BillEntity> selectBillByUserId(@Param("userId") int userId);

    /**
     * 根据账单状态查订单
     *
     * @param status
     * @return
     */
    List<BillEntity> selectBillByStatus(@Param("status") int status);

    /**
     * 继续下单
     *
     * @param userId
     * @param billState
     * @return
     */
    BillEntity queryBillId(@Param("userId") Integer userId,
                    @Param("billState") Integer billState);

    /**
     * 根据Id修改价格
     *
     * @param billId
     * @return
     */
    BillEntity updatePrice(@Param("price")BigDecimal price,
                           @Param("billId") int billId,
                           @Param("billState") int billState);
}

