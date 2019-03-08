package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsService {
    /**
     * 新增菜
     *
     * @param goodsDTO
     * @return
     */
    int insertGoods(GoodsDTO goodsDTO);

    /**
     * 更改菜
     *
     * @param goodsDTO
     * @return
     */
    int updateGoodsById(GoodsDTO goodsDTO);

    /**
     * 更改菜的库存
     *
     * @param id
     * @param num 减少量
     * @return
     */
    int reduceGoodsNumById(int id, int num);

    /**
     * 删除菜
     *
     * @param id
     * @return
     */
    int deleteGoodsById(int id);

    /**
     * 删除菜根据厨师id
     *
     * @param cookId
     * @return
     */
    int deleteGoodsByCookId(int cookId);

    /**
     * 根据厨师的id查菜
     *
     * @param cookIds
     * @return
     */
    List<GoodsDTO> selectGoodsByCookIds(List<Integer> cookIds);

    /**
     * 根据厨师的id查菜
     *
     * @param cookId
     * @return
     */
    List<GoodsDTO> selectGoodsByCookId(int cookId);

    /**
     * 根据菜名模糊查菜
     *
     * @param name
     * @return
     */
    List<GoodsDTO> selectGoodsByName(String name);

    /**
     * 根据id查菜
     *
     * @param id
     * @return
     */
    GoodsDTO selectGoodsById(int id);

    /**
     * 动态查询商品信息
     *
     * @param goodName
     * @param goodType
     * @param discount
     * @return
     */
    List<GoodsDTO> searchGoods(String goodName, Integer goodType, Integer discount);

    /**
     * 根据价格区间查询商品
     *
     * @param startPrice
     * @param endPrice
     * @return
     */
    List<GoodsDTO> selectByPrice(BigDecimal startPrice, BigDecimal endPrice);
}
