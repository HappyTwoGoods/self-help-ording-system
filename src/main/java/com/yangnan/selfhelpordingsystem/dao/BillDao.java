package com.yangnan.selfhelpordingsystem.dao;

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
     * @param status
     * @return
     */
    int updateBillStatus(@Param("id") int id,
                         @Param("status") int status);

    /**
     * 更新商品信息
     * @param id
     * @param goodsInfo
     * @param price
     * @return
     */
    int updateGoodsInfoAndPrice(@Param("id") int id,
                                @Param("goods") String goodsInfo,
                                @Param("price")BigDecimal price);

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
}

