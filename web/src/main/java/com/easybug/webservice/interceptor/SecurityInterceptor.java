package com.easybug.webservice.interceptor;

import com.easybug.common.SysException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;


public class SecurityInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    public SecurityInterceptor(){
        //表示在调用方法前拦截
        super(Phase.PRE_INVOKE);
    }
    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        logger.info("开始验证webservice用户信息...");
        List<Header> headers  = soapMessage.getHeaders();
        if(headers == null || headers.size()<1){
            throw new SysException("webservice用户信息验证失败",500);
        }
        Header header = headers.get(0);
        Element el = (Element)header.getObject();
        NodeList users = el.getElementsByTagName("username");
        NodeList passwords = el.getElementsByTagName("password");
        if(users.getLength()<1){
            throw new SysException("webservice用户信息验证失败",500);
        }
        String username = users.item(0).getTextContent().trim();
        if(passwords.getLength()<1){
            throw new SysException("webservice用户信息验证失败",500);
        }
        String password = passwords.item(0).getTextContent().trim();
        if(!"liujie".equals(username) || "123456".equals(password)) {
            throw new SysException("webservice用户信息验证失败", 500);
        }
    }
}
