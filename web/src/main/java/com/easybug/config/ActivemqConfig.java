package com.easybug.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

@Configuration
@EnableJms
public class ActivemqConfig {
    @Value("${spring.activemq.broker-url}")
    private String url;
    @Value("${spring.activemq.user}")
    private String user;
    @Value("${spring.activemq.password}")
    private String password;
    @Bean
    public Destination queue(){
        ActiveMQQueue que = new ActiveMQQueue("test_queue");
        return que;
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setInitialRedeliveryDelay(11000);
        redeliveryPolicy.setMaximumRedeliveries(6);
        redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setBackOffMultiplier(2);
        redeliveryPolicy.setUseCollisionAvoidance(true);
        redeliveryPolicy.setCollisionAvoidancePercent((short)15);
        redeliveryPolicy.setMaximumRedeliveryDelay(10000);
        redeliveryPolicy.setRedeliveryDelay(11000);
        return redeliveryPolicy;
    }
    @Bean
    public ActiveMQConnectionFactory mqConnectionFactory(RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory();
        mqConnectionFactory.setBrokerURL(url);
        mqConnectionFactory.setUserName(user);
        mqConnectionFactory.setPassword(password);
        mqConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return mqConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory mqConnectionFactory){
        JmsTemplate jmsTemplate=new JmsTemplate();
        jmsTemplate.setDeliveryMode(2);//进行持久化配置 1表示非持久化，2表示持久化
        jmsTemplate.setConnectionFactory(mqConnectionFactory);
        jmsTemplate.setSessionAcknowledgeMode(4);//客户端签收模式
        return jmsTemplate;
    }
}
