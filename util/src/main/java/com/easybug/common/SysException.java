package com.easybug.common;

public class SysException extends RuntimeException{
    private String message;
    private Integer code;
    public SysException(String message,Integer code){
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SysException{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
