package com.easybug.exceptionhandler;

import com.easybug.common.ResultJson;
import com.easybug.common.SysException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController{
    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler
    public ResultJson myHandler(SysException e){
        ResultJson r = new ResultJson();
        r.setMessage(e.getMessage());
        r.setStatus(e.getCode());
        return r;
    }

    /**
     * 捕获全局异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler
    public ResultJson handler(Exception e){
        ResultJson r = new ResultJson();
        r.setMessage(e.getMessage());
        r.setStatus(500);
        return r;
    }
}
