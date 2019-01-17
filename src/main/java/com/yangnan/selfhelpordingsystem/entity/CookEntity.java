package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CookEntity {
    private Integer Id;
    private String name;
    private String telephone;
    private Integer status;
    private Date addTime;
    private Date updateTime;
}
