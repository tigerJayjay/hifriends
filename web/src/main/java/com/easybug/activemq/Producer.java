package com.easybug.activemq;

import org.apache.activemq.network.jms.JmsMesageConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

@Service("producer")
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination destination;
    public void sendMessage(String message){
        this.jmsTemplate.convertAndSend(destination,message);
    }
}
