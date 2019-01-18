package com.yangnan.selfhelpordingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsDTO implements Serializable {
    private static final long serialVersionUID = -8781778120779115922L;
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
