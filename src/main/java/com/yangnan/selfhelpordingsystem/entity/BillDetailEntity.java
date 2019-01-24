package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillDetailEntity {
    private Integer id;
    private Integer billId;
    private Integer goodsId;
    private BigDecimal price;
    private Integer num;
    private Integer status;
    private Date addTime;
    private Date updateTime;
}
