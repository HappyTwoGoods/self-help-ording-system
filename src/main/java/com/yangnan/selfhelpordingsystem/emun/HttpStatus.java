package com.yangnan.selfhelpordingsystem.emun;

/**
 * @author liuwen
 */
public enum HttpStatus {

    OK(200, "成功"),

    PARAMETER_ERROR(403, "参数错误"),

    NOT_FOUND(404, "找不到资源"),

    ERROR(500, "服务端错误");

    public int value;

    public String message;

    HttpStatus(int value, String message) {
        this.value = value;
        this.message = message;
    }

}
