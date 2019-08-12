package com.easybug.common;

import java.util.HashMap;

public class ResultJson<T>{
    private T data;
    private int status;
    private String message;
    public ResultJson(){
        this.status = 200;
        this.message = "success";
    }
    public static  ResultJson ok(String message){
        ResultJson r = new ResultJson();
        r.setMessage(message);
        return r;
    }
    public static  ResultJson ok(String message,Integer code){
        ResultJson r = new ResultJson();
        r.setStatus(code);
        r.setMessage(message);
        return r;
    }
    public static  ResultJson ok(){
      return new ResultJson();
    }
    public static  ResultJson error(){
        ResultJson r = new ResultJson();
        r.setStatus(500);
        r.setMessage("false");
        return r;
    }
    public static  ResultJson error(String message){
        ResultJson r = new ResultJson();
        r.setMessage(message);
        return r;
    }
    public static  ResultJson error(String message,Integer code){
        ResultJson r = new ResultJson();
        r.setStatus(code);
        r.setMessage(message);
        return r;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
