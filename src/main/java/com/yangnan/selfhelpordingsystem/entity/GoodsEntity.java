package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsEntity {

    private Integer id;

    private String name;

    private Integer type;

    private Integer cookId;

    private BigDecimal price;

    private Integer discount;

    private Integer limit;

    private String image;

    private Integer goodsNum;

    private String describe;

    private Date addTime;

    private Date updateTime;
}
