package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ManagerEntity {
    private Integer id;
    private String name;
    private String idCard;
    private String telephone;
    private String userName;
    private String password;
    private Date addTime;
    private Date updateTime;
}
