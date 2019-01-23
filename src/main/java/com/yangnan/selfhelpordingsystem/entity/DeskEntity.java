package com.yangnan.selfhelpordingsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DeskEntity {

    private Integer id;

    private String deskNum;

    private String describe;

    private Date addTime;

    private Date updateTime;
}
