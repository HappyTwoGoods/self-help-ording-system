package com.yangnan.selfhelpordingsystem.util;

import com.yangnan.selfhelpordingsystem.dto.CookDTO;
import com.yangnan.selfhelpordingsystem.dto.DeskDTO;
import com.yangnan.selfhelpordingsystem.dto.GoodsDTO;

import java.math.BigDecimal;

public class CreateBean {
    public static DeskDTO createDesk(String num, String describe) {
        DeskDTO deskDTO = new DeskDTO();
        deskDTO.setDescribe(describe);
        deskDTO.setDeskNum(num);
        return deskDTO;
    }

    public static CookDTO createCook(String cookName, String telephone, String nickname, String password) {
        CookDTO cookDTO = new CookDTO();
        cookDTO.setCookName(cookName);
        cookDTO.setTelephone(telephone);
        cookDTO.setNickname(nickname);
        cookDTO.setCookPassword(password);
        return cookDTO;
    }

    public static GoodsDTO createGoods(String goodsName, Integer type, BigDecimal price, Integer discount, Integer limit,
                                       String image, Integer num, String describe) {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setName(goodsName);
        goodsDTO.setType(type);
        goodsDTO.setPrice(price);
        goodsDTO.setDiscount(discount);
        goodsDTO.setLimit(limit);
        goodsDTO.setImage(image);
        goodsDTO.setGoodsNum(num);
        goodsDTO.setDescribe(describe);
        return goodsDTO;
    }
}
