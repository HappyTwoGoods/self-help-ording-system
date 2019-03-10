package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTest {
    @Resource
    private GoodsService goodsService;

    @Test
    public void insertGoodsTest() {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setName("豆浆海瑞");
        goodsDTO.setDescribe("好吃不贵");
        goodsDTO.setCookId(1);
        goodsDTO.setImage("a");
        goodsDTO.setLimit(10);
        goodsDTO.setPrice(BigDecimal.valueOf(90));
        goodsDTO.setGoodsNum(10);
        goodsDTO.setDiscount(10);
        goodsDTO.setType(1);
        int i = goodsService.insertGoods(goodsDTO);
        Assert.assertEquals(i, 1);
    }

    @Test
    public void updateGoodsByIdTest() {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setId(10);
        goodsDTO.setName("麻辣烫");
        goodsDTO.setDescribe("好不贵");
        goodsDTO.setCookId(3);
        goodsDTO.setImage("B");
        goodsDTO.setLimit(0);
        goodsDTO.setPrice(BigDecimal.valueOf(60));
        goodsDTO.setGoodsNum(4);
        goodsDTO.setDiscount(5);
        goodsDTO.setType(2);
        int i = goodsService.updateGoodsById(goodsDTO);
        Assert.assertEquals(1, i);
    }

    @Test
    public void reduceGoodNumByIdTest() {
        int i = goodsService.reduceGoodsNumById(10, 2);
        Assert.assertEquals(1, i);
    }

    @Test
    public void deleteGoodsByIdTest() {
        int i = goodsService.deleteGoodsById(2);
        Assert.assertEquals(1, i);
    }

    @Test
    public void deleteGoodsByCookIdTest() {
        int i = goodsService.deleteGoodsByCookId(1);
        Assert.assertEquals(3, i);
    }

    @Test
    public void selectGoodsByNameTest() {
        List<GoodsDTO> list = goodsService.selectGoodsByName("宫保");
        Assert.assertNotNull(list);
    }

    @Test
    public void selectGoodByCookIds() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookIds(integers);
        Assert.assertNotNull(goodsDTOS);
    }

    @Test
    public void selectGoodIdByCookId() {
        List<GoodsDTO> goodsDTOS = goodsService.selectGoodsByCookId(1);
        for (GoodsDTO a : goodsDTOS) {
            System.out.println(a);
        }
    }

    @Test
    public void selectGoodById() {
        GoodsDTO goodsDTO = goodsService.selectGoodsById(1);
        Assert.assertNotNull(goodsDTO);
    }

    @Test
    public void searchGoods() {
        List<GoodsDTO> goodsEntityList = goodsService.searchGoods(null, null, null);
        System.out.println(goodsEntityList);
    }

    @Test
    public void selectByPrice() {
        List<GoodsDTO> goodsDTOList = goodsService.selectByPrice(BigDecimal.valueOf(100), BigDecimal.valueOf(200));
        System.out.println(goodsDTOList);
    }
}
