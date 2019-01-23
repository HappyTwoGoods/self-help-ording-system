package com.yangnan.selfhelpordingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeskDTO implements Serializable {

    private Integer id;
    private Integer deskNum;
    private String describe;
    private Date addTime;
    private Date updateTime;
}
