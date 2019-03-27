package com.yangnan.selfhelpordingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillDTO implements Serializable {
    private static final long serialVersionUID = -5810583702720403879L;
    private Integer id;
    private BigDecimal price;
    private Integer userId;
    private Integer deskNum;
    private Integer payType;
    private Integer status;
    private Date addTime;
    private Date updateTime;
}
