package com.bbc.exception;

/**
 * @Title: BusinessException
 * @Author WangHaoWei
 * @Package com.bbc
 * @Date 2024/1/8 16:12
 * @description:
 */

/**
 * @author ：LiuShihao
 * @date ：Created in 2020/9/3 9:45 上午
 * @desc ：
 */

public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 907335315335226691L;

    private int code;

    private String message;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseException() {
    }

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}


