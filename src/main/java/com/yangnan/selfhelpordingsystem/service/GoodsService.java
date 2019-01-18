package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.entity.GoodsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GoodsService {
    /**
     * 新增菜
     *
     * @param goodsDTO
     * @return
     */
    int insertGoods(@Param("goods") GoodsDTO goodsDTO);

    /**
     * 更改菜
     *
     * @param goodsDTO
     * @return
     */
    int updateGoodsById(@Param("goods") GoodsDTO goodsDTO);

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
    int deleteGoodsByCookId(@Param("cookId") int cookId);

    /**
     * 根据厨师的id查菜
     *
     * @param cookIds
     * @return
     */
    List<GoodsDTO> selectGoodsByCookIds(@Param("cookIds") List<Integer> cookIds);

    /**
     * 根据菜名模糊查菜
     *
     * @param name
     * @return
     */
    List<GoodsDTO> selectGoodsByName(@Param("name") String name);

    /**
     * 根据id查菜
     *
     * @param id
     * @return
     */
    GoodsDTO selectGoodsById(@Param("id") int id);
}
