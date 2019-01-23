package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CookEntity {
    
    private Integer id;
    private String cookName;
    private String telephone;
    private Integer cookStatus;
    private Date addTime;
    private Date updateTime;
}
