package com.yangnan.selfhelpordingsystem.impl;

import com.yangnan.selfhelpordingsystem.dao.GoodsDao;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import com.yangnan.selfhelpordingsystem.entity.GoodsEntity;
import com.yangnan.selfhelpordingsystem.service.GoodsService;
import com.yangnan.selfhelpordingsystem.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsDao goodsDao;

    @Override
    public int insertGoods(GoodsDTO goodsDTO) {
        if (goodsDTO == null) {
            return 0;
        }
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(goodsDTO, goodsEntity);
        return goodsDao.insertGoods(goodsEntity);
    }

    @Override
    public int updateGoodsById(GoodsDTO goodsDTO) {
        if (goodsDTO == null || goodsDTO.getId() == null || goodsDTO.getId() < 1) {
            return 0;
        }
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(goodsDTO, goodsEntity);
        return goodsDao.updateGoodsById(goodsEntity);
    }

    @Override
    public int reduceGoodsNumById(int id, int num) {
        if (id < 1 || num < 1) {
            return 0;
        }
        return goodsDao.reduceGoodsNumById(id, num);
    }

    @Override
    public int deleteGoodsById(int id) {
        if (id < 1) {
            return 0;
        }
        return goodsDao.deleteGoodsById(id);
    }

    @Override
    public int deleteGoodsByCookId(int cookId) {
        if (cookId < 1) {
            return 0;
        }
        return goodsDao.deleteGoodsByCookId(cookId);
    }

    @Override
    public List<GoodsDTO> selectGoodsByCookIds(List<Integer> cookIds) {
        if (CollectionUtils.isEmpty(cookIds)) {
            return new ArrayList<>();
        }
        List<GoodsEntity> goodsEntities = goodsDao.selectGoodsByCookIds(cookIds);
        return BeansListUtils.copyListProperties(goodsEntities, GoodsDTO.class);
    }

    @Override
    public List<GoodsDTO> selectGoodsByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new ArrayList<>();
        }
        List<GoodsEntity> goodsEntities = goodsDao.selectGoodsByName(name);
        return BeansListUtils.copyListProperties(goodsEntities, GoodsDTO.class);
    }

    @Override
    public GoodsDTO selectGoodsById(int id) {
        if (id < 1) {
            return new GoodsDTO();
        }
        GoodsEntity goodsEntity = goodsDao.selectGoodsById(id);
        GoodsDTO goodsDTO = new GoodsDTO();
        BeanUtils.copyProperties(goodsEntity, goodsDTO);
        return goodsDTO;
    }

    @Override
    public List<GoodsDTO> searchGoods(String goodName, Integer goodType, Integer discount) {
        List<GoodsEntity> goodsEntities = goodsDao.searchGoods(goodName,goodType,discount);
        return BeansListUtils.copyListProperties(goodsEntities, GoodsDTO.class);
    }

    @Override
    public List<GoodsDTO> selectByPrice(BigDecimal startPrice, BigDecimal endPrice) {
        if (startPrice.compareTo(endPrice) > 0){
            return Collections.emptyList();
        }
        List<GoodsEntity> goodsEntityList = goodsDao.selectByPrice(startPrice,endPrice);
        return BeansListUtils.copyListProperties(goodsEntityList, GoodsDTO.class);
    }
}
