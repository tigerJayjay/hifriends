package com.easybug.activemq;

import org.apache.commons.lang.StringUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component("consumer")
public class Consumer {
    @JmsListener(destination = "test_queue")
    public void receiveQueue(String text){
        if(!StringUtils.isBlank(text)){
            System.out.println(text);
        }
    }
}
