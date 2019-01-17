package com.yangnan.selfhelpordingsystem.common;

import com.yangnan.selfhelpordingsystem.emun.HttpStatus;
import lombok.Getter;


public class CommonResult<Content> {

    /**
     * 返回码
     */
    @Getter
    private int code;

    /**
     * 是否成功
     */
    @Getter
    private boolean success = false;

    /**
     * 提示信息
     */
    @Getter
    private String message;

    /**
     * 返回数据
     */
    @Getter
    private Content data;

    public CommonResult() {

    }

    public static <Content> CommonResult<Content> success() {
        CommonResult<Content> result = new CommonResult<>();
        result.setCode(HttpStatus.OK.value);
        result.setSuccess(true);
        result.setMessage("操作成功");
        return result;
    }

    public static <Content> CommonResult<Content> success(Content data) {
        CommonResult<Content> result = success();
        return result.setData(data);
    }

    public static <Content> CommonResult<Content> success(String message) {
        return success(HttpStatus.OK.value, message);
    }
    
    public static <Content> CommonResult<Content> success(int code, String message) {
        CommonResult<Content> result = new CommonResult<>();
        result.setCode(code);
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

    public static <Content> CommonResult<Content> fail(HttpStatus status) {
        return fail(status.value, status.message);
    }

    public static <Content> CommonResult<Content> fail(int code) {
        return fail(code, "");
    }

    public static <Content> CommonResult<Content> fail(String message) {
        return fail(HttpStatus.ERROR.value, message);
    }

    public static <Content> CommonResult<Content> fail(int code, String message) {
        CommonResult<Content> result = new CommonResult<>();
        result.setCode(code);
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public CommonResult<Content> setCode(int code) {
        this.code = code;
        return this;
    }

    public CommonResult<Content> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public CommonResult<Content> setMessage(String message) {
        this.message = message;
        return this;
    }

    public CommonResult<Content> setData(Content data) {
        this.data = data;
        return this;
    }
}
