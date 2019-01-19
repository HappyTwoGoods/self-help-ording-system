package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserAccountEntity {
    private Integer id;
    private String name;
    private String password;
    private BigDecimal price;
    private Date addTime;
    private Date updateTime;
}
