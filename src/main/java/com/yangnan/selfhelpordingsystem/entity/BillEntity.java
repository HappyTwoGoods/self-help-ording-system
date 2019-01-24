package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillEntity {

    private Integer id;

    private String goodsInfo;

    private BigDecimal price;

    private Integer userId;

    private Integer payType;

    private Integer status;

    private Date addTime;

    private Date updateTime;
}
