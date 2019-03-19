package com.yangnan.selfhelpordingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CookDTO implements Serializable {

    private static final long serialVersionUID = 9014072451710733916L;
    private Integer id;
    private String cookName;
    private String telephone;
    private Integer cookStatus;
    private String nickname;
    private String cookPassword;
    private Date addTime;
    private Date updateTime;
}
