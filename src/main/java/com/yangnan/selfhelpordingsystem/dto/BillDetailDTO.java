package com.yangnan.selfhelpordingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillDetailDTO implements Serializable {
    private static final long serialVersionUID = 3638980306983241940L;
    private Integer id;
    private Integer billId;
    private Integer goodsId;
    private BigDecimal price;
    private Integer num;
    private Integer status;
    private Date addTime;
    private Date updateTime;
}
