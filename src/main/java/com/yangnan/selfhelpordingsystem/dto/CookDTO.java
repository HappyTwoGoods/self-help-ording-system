package com.yangnan.selfhelpordingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CookDTO implements Serializable {

    private Integer Id;
    private String cookName;
    private String telephone;
    private Integer cookStatus;
    private Date addTime;
    private Date updateTime;
}
