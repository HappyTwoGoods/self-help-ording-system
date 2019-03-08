package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.entity.GoodsEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


public interface GoodsDao {
    /**
     * 新增菜
     *
     * @param goods
     * @return
     */
    int insertGoods(@Param("goods") GoodsEntity goods);

    /**
     * 更改菜
     *
     * @param goodsEntity
     * @return
     */
    int updateGoodsById(@Param("goods") GoodsEntity goodsEntity);

    /**
     * 更改菜的库存
     *
     * @param id
     * @param num 减少量
     * @return
     */
    int reduceGoodsNumById(@Param("id") int id,
                           @Param("num") int num);

    /**
     * 删除菜
     *
     * @param id
     * @return
     */
    int deleteGoodsById(@Param("id") int id);

    /**
     * 删除菜根据厨师id
     *
     * @param cookId
     * @return
     */
    int deleteGoodsByCookId(@Param("cookId")int cookId);

    /**
     * 根据厨师的id查菜
     *
     * @param cookIds
     * @return
     */
    List<GoodsEntity> selectGoodsByCookIds(@Param("cookIds") List<Integer> cookIds);

    /**
     * 根据厨师的id查菜
     *
     * @param cookId
     * @return
     */
    List<GoodsEntity> selectGoodsByCookId(@Param("cookId") int cookId);

    /**
     * 根据菜名模糊查菜
     *
     * @param name
     * @return
     */
    List<GoodsEntity> selectGoodsByName(@Param("name") String name);

    /**
     * 根据id查菜
     *
     * @param id
     * @return
     */
    GoodsEntity selectGoodsById(@Param("id") int id);

    /**
     * 动态搜索查询商品信息
     *
     * @param goodName
     * @param goodType
     * @param discount
     * @return
     */
    List<GoodsEntity> searchGoods(@Param("goodName") String goodName,
                                  @Param("goodType") Integer goodType,
                                  @Param("discount") Integer discount);

    /**
     * 根据商品价格区间查找商品信息
     *
     * @param startPrice
     * @param endPrice
     * @return
     */
    List<GoodsEntity> selectByPrice(@Param("startPrice") BigDecimal startPrice,
                                    @Param("endPrice") BigDecimal endPrice);
}
