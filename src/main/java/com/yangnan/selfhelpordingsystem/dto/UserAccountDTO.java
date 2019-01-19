package com.yangnan.selfhelpordingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserAccountDTO implements Serializable {
    private static final long serialVersionUID = 9197670387382290314L;
    private Integer id;
    private String name;
    private String password;
    private BigDecimal price;
    private Date addTime;
    private Date updateTime;
}
