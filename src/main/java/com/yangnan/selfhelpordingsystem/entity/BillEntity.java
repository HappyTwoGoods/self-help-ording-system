package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillEntity {
    private Integer id;
    private String goodsInfo;
    private Integer status;
    private BigDecimal price;
    private Date addTime;
    private Date updateTime;
}
